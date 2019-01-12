package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Hyunjin.Data.AlarmData
import com.computer.inu.myworkinggings.Moohyeon.Data.MyAlarmData

class GetAlarmResponse (
        var status : Int,
        var  message : String,
        var data : ArrayList<AlarmData>

)