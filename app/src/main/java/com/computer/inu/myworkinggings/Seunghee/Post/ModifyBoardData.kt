package com.computer.inu.myworkinggings.Seunghee.Post

import com.computer.inu.myworkinggings.Seunghee.GET.ReplyData

data class ModifyBoardData(
        val boardId : Int?,
        val writerId : Int?,
        val writer : String?,
        val company : String?,
        val title : String?,
        val content : String?,
        val share : Int?,
        val time : String?,
        val category: String?,
        val image : ArrayList<String?>,
        val keyword : ArrayList<String?>,
        val replys : ArrayList<ReplyData>?,
        val numOfReply : Int?,
        val recommender : Int?
)