package com.zyqzyq.hinao.data

/*
*扩展发送端,简化代码
*/

fun stopAllBehavior(){
    //停止所有运动
    sendmsg("00")
}

fun startBehavior(msg: String){
    //开始应用程序
    sendmsg("01"+msg)
}

fun startSay(msg: String){
    //发送机器人要说的文字
    sendmsg("02"+"01"+msg)
}

fun startSayLocal(msg: String){
    //发送问题并调用机器人本地剧本进行回答
    sendmsg("02"+"02"+msg)
}

fun startMove(msg: String){
    //移动控制 01 02 03 04 分别代表上下左右方向
    sendmsg("03"+msg)
}

fun getBehaviorList(){
    //获取机器人应用程序列表
    sendmsg("04")
}


