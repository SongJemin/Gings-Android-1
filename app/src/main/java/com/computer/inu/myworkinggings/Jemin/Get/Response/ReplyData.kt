package com.computer.inu.myworkinggings.Jemin.Get.Response

data class ReplyData (
     var replyId : Int?,
     var writerId : Int?,
     var writer : String?,
     var content : String?,
     var writeTime : String?,
     var images : ArrayList<String>?,
     var recommender : Int?
)