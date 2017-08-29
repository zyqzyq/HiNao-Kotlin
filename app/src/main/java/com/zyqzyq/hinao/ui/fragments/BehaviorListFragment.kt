package com.zyqzyq.hinao.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyqzyq.hinao.R
import com.zyqzyq.hinao.ui.App
import com.zyqzyq.hinao.ui.adapters.BehaviorListAdapter
import kotlinx.android.synthetic.main.fragment_behaviorlist.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import android.R.id.message
import android.os.Handler
import android.os.Message
import com.zyqzyq.hinao.data.UDPServer
import com.zyqzyq.hinao.data.getBehaviorList
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import com.alibaba.fastjson.JSON
import com.zyqzyq.hinao.data.behavior
import com.zyqzyq.hinao.data.behaviorClass

class BehaviorListFragment : Fragment(){
    //创建主线程的Handler

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val items = listOf("小苹果","骑马舞","上海舞")
        val adapter = BehaviorListAdapter(items)
        val mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                println("main Handler")
                Log.d("UDP","HANDLER")
                val b = msg.data
                val behaviorList = b["BehaviorList"].toString()
                try {
                    val items2 = JSON.parseArray(behaviorList, String::class.java)
                    if (items2 != null) {
                        val adapter2 = BehaviorListAdapter(items2)
                        activity.behaviorList.adapter = adapter2
                        Log.d("UDP", behaviorList)
                        activity.toast("成功刷新")
                        //向子线程发送消息
                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                }

            }
        }
        behaviorList.layoutManager = LinearLayoutManager(App.instance)
        behaviorList.adapter = adapter
        searchBehaviorBtn.setOnClickListener {

            val myUdpSer = Thread(UDPServer(mHandler))
            activity.toast("刷新程序列表")
            myUdpSer.start()
        }


    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_behaviorlist,container,false)
    }

}