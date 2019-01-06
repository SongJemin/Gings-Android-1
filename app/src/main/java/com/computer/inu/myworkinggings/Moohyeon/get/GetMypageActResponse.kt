package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Moohyeon.Data.MyActData
import com.computer.inu.myworkinggings.Moohyeon.Data.MyIntroduceData

class GetMypageActResponse (
        var status : Int,
        var  message : String,
        var  data : ArrayList<MyActData>
)