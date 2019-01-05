package com.computer.inu.myworkinggings.Jemin.Get.Response.GetData

data class BoardData (
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
      var numOfReply : Int?,
      var recommender : Int?
)