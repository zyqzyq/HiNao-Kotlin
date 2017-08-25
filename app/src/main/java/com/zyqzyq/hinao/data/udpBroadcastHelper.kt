package com.zyqzyq.hinao.data


import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import org.jetbrains.anko.bundleOf
import java.io.IOException
import java.net.*


public fun sendmsg(msg:String){

    Thread(BroadCastUdp(msg)).start()
}

class BroadCastUdp(val msg:String) :Thread(){
    var s: DatagramSocket? = null
    companion object {
        val server_ip = "255.255.255.255"
        val server_port = 10024
    }
    override  fun run() {
        send(msg)
        super.run()
    }
    fun send(message: String?){
        var msg = message?:"Hello"
        Log.d("UDP",msg)
        try {
            s = DatagramSocket()
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        var local: InetAddress? = null
        try {
            local = InetAddress.getByName(server_ip)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }
        val p = DatagramPacket(msg.toByteArray(), msg.length, local,
                server_port)
        try {
            s!!.send(p)
            s!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}


class UDPServer(mHandler: Handler):Runnable{
    val server_port = 10024
    var result: String? = null
    val mHandler = mHandler
    @Throws(IOException::class)
    override fun run(){

        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(server_port)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getBehaviorList()
        while (true) {
            val data = ByteArray(1024)
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
