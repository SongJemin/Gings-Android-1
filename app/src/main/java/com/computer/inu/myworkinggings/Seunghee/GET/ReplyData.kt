package com.computer.inu.myworkinggings.Seunghee.GET

data class ReplyData (
        val replyId : Int?,
        val writeId : Int?,
        val writer : String?,
        val content : String?,
        val writeTime : String?,
        val images : ArrayList<String?>,
        val recommender : Int?
)