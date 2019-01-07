package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.GetActiveData
import com.computer.inu.myworkinggings.Moohyeon.Data.MyActData
import com.computer.inu.myworkinggings.Moohyeon.Data.MyIntroduceData

data class GetMypageActResponse (
        var  status : Int,
        var  message : String,
        var  data : ArrayList<GetActiveData>
)