package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryData

data class GetDirectoryListResponse (
        var status : Int?,
        var  message : String?,
        var  data : ArrayList<DirectoryData>
)