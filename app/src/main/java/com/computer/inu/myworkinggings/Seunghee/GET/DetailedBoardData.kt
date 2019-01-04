package com.computer.inu.myworkinggings.Seunghee.GET

data class DetailedBoardData(
        val boardId : Int?,
        val writerId : Int?,
        val writer : String?,
        val field : String?,
        val company : String?,
        val title : String?,
        val content : String?,
        val share : Int?,
        val time : String?,
        val category : String?,
        val images : ArrayList<String?>,
        val keywords : ArrayList<String?>,
        val replys : ArrayList<ReplyData?>,
        val numOfReply : Int?,
        val recommender : Int?
)