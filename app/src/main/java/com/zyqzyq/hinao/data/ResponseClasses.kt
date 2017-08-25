package com.zyqzyq.hinao.data

data class UnderstandData(val service: String, val text: String , val answer: AnswerData,val rc : Int)
data class AnswerData(val answerType: String, val text: String)