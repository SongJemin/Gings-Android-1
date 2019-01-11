package com.computer.inu.myworkinggings.Seunghee.GET

data class ReplyData (
        val replyId : Int?,
        val writeId : Int?,
        val writerImage : String?,
        val writer : String?,
        val content : String?,
        val writeTime : String?,
        val images : ArrayList<String?>,
        val recommender : Int?,
        val likeChk : Boolean
)