package com.computer.inu.myworkinggings.Jemin.Get.Response

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.GetIntroduceData

data class GetMyIntroduceResponse (
      var status : Int,
      var message : String,
      var data : ArrayList<GetIntroduceData>
)