package com.zyqzyq.hinao.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONObject
import com.iflytek.sunflower.FlowerCollector
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.data.chatModel
import com.zyqzyq.hinao.ui.adapters.NaoChatAdapter
import kotlinx.android.synthetic.main.activity_nao_chatting.*
import com.iflytek.cloud.*
import com.iflytek.aiui.AIUIConstant
import com.iflytek.aiui.AIUIAgent
import com.iflytek.aiui.AIUIMessage
import java.io.IOException
import com.iflytek.aiui.AIUIListener
import com.zyqzyq.hinao.data.UnderstandData


class NaoChatting : Activity(), OnClickListener {
    // AIUI语义（语音到语义）。
    private var mAIUIAgent: AIUIAgent? = null
    private var mAIUIState = AIUIConstant.STATE_IDLE

    // 语音合成对象
    private var mTts: SpeechSynthesizer? = null
    // 引擎类型
    private var mEngineType = SpeechConstant.TYPE_CLOUD
    // 缓冲进度
    private var mPercentForBuffering = 0
    // 播放进度
    private var mPercentForPlaying = 0
    private var mToast: Toast? = null
    private var chatDataList: ArrayList<chatModel>? = ArrayList()

    @SuppressLint("ShowToast")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_nao_chatting)
        val chatdata1 = chatModel("你好",1)
        val chatdata2 = chatModel("你好，很高兴见到你",0)
        chatDataList?.add(chatdata1)
        chatDataList?.add(chatdata2)
        chattingView.layoutManager = LinearLayoutManager(this)
        chattingView.adapter = NaoChatAdapter(chatDataList!!)

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this@NaoChatting, mTtsInitListener)

        mToast = Toast.makeText(this@NaoChatting, "", Toast.LENGTH_SHORT)

        start_understander.setOnClickListener(this@NaoChatting)
    }

    override fun onClick(view: View?) {
        if( !checkAIUIAgent() ){
            return
        }
        when(view?.id){

            R.id.start_understander -> {
                setTtsParam()
                startVoiceNlp()
            }

        }
    }
    /**
     * 创建AIUIAgent
     * */
    private fun checkAIUIAgent(): Boolean {
        if (null == mAIUIAgent) {
            Log.i(TAG, "create aiui agent")
            mAIUIAgent = AIUIAgent.createAgent(this, getAIUIParams(), mAIUIListener)
            val startMsg = AIUIMessage(AIUIConstant.CMD_START, 0, 0, null, null)
            mAIUIAgent?.sendMessage(startMsg)
        }

        if (null == mAIUIAgent) {
            val strErrorTip = "创建 AIUI Agent 失败！"
            showTip(strErrorTip)
        }

        return null != mAIUIAgent
    }

    private fun getAIUIParams(): String {
        var params = ""

        val assetManager = resources.assets
        try {
            val ins = assetManager.open("cfg/aiui_phone.cfg")
            val buffer = ByteArray(ins.available())

            ins.read(buffer)
            ins.close()

            params = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return params
    }
    /**
     * 初始化监听器（语音合成）。
     */
    private val mTtsInitListener = InitListener { code ->
        Log.d(TAG, "InitListener init() code = " + code)
        if (code != ErrorCode.SUCCESS) {
            showTip("初始化失败,错误码：" + code)
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    }

    /**
     * 开始语义理解
     **/
    private fun startVoiceNlp() {
        Log.i(TAG, "start voice nlp")

        // 先发送唤醒消息，改变AIUI内部状态，只有唤醒状态才能接收语音输入
        if (AIUIConstant.STATE_WORKING != this.mAIUIState) {
            val wakeupMsg = AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null)
            mAIUIAgent?.sendMessage(wakeupMsg)
        }

        // 打开AIUI内部录音机，开始录音
        val params = "sample_rate=16000,data_type=audio"
        val writeMsg = AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, params, null)
        mAIUIAgent?.sendMessage(writeMsg)
    }
    internal var ret = 0// 函数调用返回值

    fun start_tts_play(text: String){
        FlowerCollector.onEvent(this@NaoChatting, "tts_play")


        // 设置参数
        // setParam()
        val code = mTts!!.startSpeaking(text, mTtsListener)
        //			/**
        //			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
        //			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
        //			*/
        //			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
        //			int code = mTts.synthesizeToUri(text, path, mTtsListener);

        if (code != ErrorCode.SUCCESS) {
            showTip("语音合成失败,错误码: " + code)
        }
    }


    /**
     * 语义理解回调。
     */

    /**
     * 合成回调监听。
     */
    private val mTtsListener = object : SynthesizerListener {

        override fun onSpeakBegin() {
            showTip("开始播放")
        }

        override fun onSpeakPaused() {
            showTip("暂停播放")
        }

        override fun onSpeakResumed() {
            showTip("继续播放")
        }

        override fun onBufferProgress(percent: Int, beginPos: Int, endPos: Int,
                                      info: String) {
            // 合成进度
            mPercentForBuffering = percent
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying))
        }

        override fun onSpeakProgress(percent: Int, beginPos: Int, endPos: Int) {
            // 播放进度
            mPercentForPlaying = percent
            showTip(String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying))
        }

        override fun onCompleted(error: SpeechError?) {
            if (error == null) {
                showTip("播放完成")
//                start_understander_play()
            } else {
                showTip(error.getPlainDescription(true))
            }
        }

        override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    }


    private fun showTip(str: String) {
        mToast!!.setText(str)
        mToast!!.show()
    }

    private fun showTip(str: String, duration: Int) {
        val lastDuration = mToast!!.duration
        mToast!!.setText(str)
        mToast!!.duration = duration
        mToast!!.show()
        mToast!!.duration = lastDuration
    }


    /**
     * 参数设置
     * @param param
     * *
     * @return
     */

    fun setTtsParam(){
        // 清空参数
        mTts!!.setParameter(SpeechConstant.PARAMS, null)
        // 根据合成引擎设置相应参数
        mTts!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        // 设置在线合成发音人
        mTts!!.setParameter(SpeechConstant.VOICE_NAME, "xiaowanzi")
        //设置合成语速
        mTts!!.setParameter(SpeechConstant.SPEED, "50")
        //设置合成音调
        mTts!!.setParameter(SpeechConstant.PITCH, "50")
        //设置合成音量
        mTts!!.setParameter(SpeechConstant.VOLUME, "50")

        //设置播放器音频流类型
        mTts!!.setParameter(SpeechConstant.STREAM_TYPE, "3")
        // 设置播放合成音频打断音乐播放，默认为true
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //mTts!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        //mTts!!.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav")
    }

    private val mAIUIListener = AIUIListener { event ->
        when (event.eventType) {
            AIUIConstant.EVENT_WAKEUP -> {
                Log.i(TAG, "on event: " + event.eventType)
                showTip("进入识别状态")
            }

            AIUIConstant.EVENT_RESULT -> {
                Log.i(TAG, "on event: " + event.eventType)
                try {

                    val bizParamJson = JSONObject(event.info)
                    val data = bizParamJson.getJSONArray("data").getJSONObject(0)
                    val params = data.getJSONObject("params")
                    val content = data.getJSONArray("content").getJSONObject(0)

                    if (content.has("cnt_id")) {
                        val cnt_id = content.getString("cnt_id")
                        val cntJson = JSONObject(String(event.data.getByteArray(cnt_id)))

                        val sub = params.optString("sub")
                        if ("nlp" == sub) {
                            // 解析得到语义结果
                            val resultStr = cntJson.optString("intent")
                            Log.i(TAG, resultStr)
                            var answer = "暂未找到相关答案"
                            val ResponseData = Gson().fromJson(resultStr, UnderstandData::class.java)
                            val question = chatModel(ResponseData.text,1)
                            chatDataList?.add(question)
                            chattingView.adapter = NaoChatAdapter(chatDataList!!)
                            if (ResponseData.rc==0){
                                answer = ResponseData.answer.text
                            }
                            val answerModel = chatModel(answer,0)
                            chatDataList?.add(answerModel)
                            chattingView.adapter = NaoChatAdapter(chatDataList!!)
                            start_tts_play(answer)
                        }
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            AIUIConstant.EVENT_ERROR -> {
                Log.i(TAG, "on event: " + event.eventType)
            }

            AIUIConstant.EVENT_VAD -> {
                if (AIUIConstant.VAD_BOS == event.arg1) {
                    showTip("找到vad_bos")
                } else if (AIUIConstant.VAD_EOS == event.arg1) {
                    showTip("找到vad_eos")
                } else {
                    showTip("" + event.arg2)
                }
            }

            AIUIConstant.EVENT_START_RECORD -> {
                Log.i(TAG, "on event: " + event.eventType)
                showTip("开始录音")
            }

            AIUIConstant.EVENT_STOP_RECORD -> {
                Log.i(TAG, "on event: " + event.eventType)
                showTip("停止录音")
            }

            AIUIConstant.EVENT_STATE -> {    // 状态事件
                mAIUIState = event.arg1

                if (AIUIConstant.STATE_IDLE == mAIUIState) {
                    // 闲置状态，AIUI未开启
                    showTip("STATE_IDLE")
                } else if (AIUIConstant.STATE_READY == mAIUIState) {
                    // AIUI已就绪，等待唤醒
                    showTip("STATE_READY")
                } else if (AIUIConstant.STATE_WORKING == mAIUIState) {
                    // AIUI工作中，可进行交互
                    showTip("STATE_WORKING")
                }
            }

            AIUIConstant.EVENT_CMD_RETURN -> {
                if (AIUIConstant.CMD_UPLOAD_LEXICON == event.arg1) {
                    showTip("上传" + if (0 == event.arg2) "成功" else "失败")
                }
            }

            else -> {
            }
        }
    }

    override fun onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(this@NaoChatting)
        FlowerCollector.onPageStart(TAG)
        super.onResume()
    }

    override fun onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG)
        FlowerCollector.onPause(this@NaoChatting)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (null != this.mAIUIAgent) {
            val stopMsg = AIUIMessage(AIUIConstant.CMD_STOP, 0, 0, null, null)
            mAIUIAgent?.sendMessage(stopMsg)

            this.mAIUIAgent?.destroy()
            this.mAIUIAgent = null
        }
    }

    companion object {
        private val TAG = NaoChatting::class.java.simpleName

        val errorTip = "请确认是否有在 aiui.xfyun.cn 配置语义。（另外，已开通语义，但从1115（含1115）以前的SDK更新到1116以上版本SDK后，语义需要重新到 aiui.xfyun.cn 配置）"
    }

}
