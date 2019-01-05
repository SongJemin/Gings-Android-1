package com.computer.inu.myworkinggings.Jemin.Get.Response

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.ProfileImageUrl

data class GetProfileImgUrlResponse (
        var status : Int,
        var messge : String,
        var data : ProfileImageUrl
)