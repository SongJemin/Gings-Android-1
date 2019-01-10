package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Moohyeon.Data.UserPageData

data class GetMypageResponse (
        var status : String,
        var message : String,
        var data : UserPageData
)