package com.computer.inu.myworkinggings.Jemin.Get.Response.GetData

import com.computer.inu.myworkinggings.Jemin.Get.Response.ReplyData

data class GetActiveData (
        var boardId : Int?,
        var writerId : Int?,
        var writer : String?,
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