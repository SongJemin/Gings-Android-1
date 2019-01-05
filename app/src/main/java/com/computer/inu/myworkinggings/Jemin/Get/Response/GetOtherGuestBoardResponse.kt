package com.computer.inu.myworkinggings.Jemin.Get.Response

import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem

data class GetOtherGuestBoardResponse (
      var status : Int?,
      var message : String?,
      var data : ArrayList<GuestBoardItem>
)