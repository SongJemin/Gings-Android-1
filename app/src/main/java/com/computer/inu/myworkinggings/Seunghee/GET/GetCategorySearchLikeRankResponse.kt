package com.computer.inu.myworkinggings.Seunghee.GET

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData

data class GetCategorySearchLikeRankResponse (
        val status : Int?,
        val message : String?,
        val data : ArrayList<BoardData>
)
