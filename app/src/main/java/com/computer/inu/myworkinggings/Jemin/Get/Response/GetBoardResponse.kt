package com.computer.inu.myworkinggings.Jemin.Get.Response

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData

data class GetBoardResponse (

        var status : Int?,
        var message : String?,
        var data : ArrayList<BoardData>
)