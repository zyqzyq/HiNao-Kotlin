package com.zyqzyq.hinao.data


import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.zyqzyq.hinao.ui.App
import com.zyqzyq.hinao.ui.activities.MainActivity
import org.jetbrains.anko.bundleOf
import java.io.IOException
import java.net.*


public fun sendmsg(msg:String){

    Thread(BroadCastUdp(msg)).start()
}

class BroadCastUdp(val msg:String) :Thread(){
    var s: DatagramSocket? = null

    override  fun run() {
        send(msg)
        super.run()
    }
    fun send(message: String?){
        val msg = message?:"Hello"
        Log.d("UDP",msg)
        try {
            s = DatagramSocket()
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        var local: InetAddress? = null
        try {
            local = InetAddress.getByName(App.server_ip)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
        val buf = msg.toByteArray()
        val p = DatagramPacket(buf, buf.size, local,
                App.server_port!!)
        try {
            s!!.send(p)
            s!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}


class UDPServer(mHandler: Handler):Runnable{
    var result: String? = null
    val mHandler = mHandler
    @Throws(IOException::class)
    override fun run(){

        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(App.server_port!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getBehaviorList()
        while (true) {
            val data = ByteArray(10240)
            val packet = DatagramPacket(data, data.size)
            socket?.receive(packet)?:break
            result = String(packet.data, packet.offset, packet.length)
            println("receive client's data: " + result)
            Log.d("UDP Receive",result)
            if (result != "04"){
                val message = Message()
                message.what = 1
                val bundle = Bundle()
                bundle.putString("BehaviorList", result)
                message.data = bundle
                mHandler.sendMessage(message)
                break
            }
        }
        socket?.close()
    }

}
