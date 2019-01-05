package com.computer.inu.myworkinggings.Jemin.Get.Response

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.GetActiveData

data class GetOtherActiveResponse (
      var status : Int,
      var message : String,
      var data : ArrayList<GetActiveData>
)