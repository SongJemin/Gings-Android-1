package com.computer.inu.myworkinggings.Moohyeon.get

import com.computer.inu.myworkinggings.Moohyeon.Data.DirectorySearchData

data class GetDirectorySearchResponse (
        var status : String,
        var message : String,
        var data : ArrayList<DirectorySearchData>
)