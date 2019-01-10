package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Moohyeon.Data.GuestBoardData

data class GetGuestBoardResponse (
        var status : String,
        var message : String,
        var data : ArrayList <GuestBoardItem>
)