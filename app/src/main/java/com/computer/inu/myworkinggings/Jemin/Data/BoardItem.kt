package com.computer.inu.myworkinggings.Jemin.Data

import com.computer.inu.myworkinggings.Jemin.Get.Response.ReplyData

data class BoardItem (
        var boardId : Int?,
        var writerId : Int?,
        var title : String?,
        var content : String?,
        var share : Int?,
        var time : String?,
        var category : String?,
        var images : ArrayList<String>,
        var keywords : ArrayList<String>,
        var replys : ArrayList<ReplyData>,
        var recommender : Int?
)