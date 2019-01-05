package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Moohyeon.Data.MyIntroduceData

data class GetMypageIntroduceResponse (
    var status : Int,
    var  message : String,
    var  data : MyIntroduceData
)