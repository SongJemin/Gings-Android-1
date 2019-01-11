package com.computer.inu.myworkinggings.Moohyeon.Data

data class UserPageData (
        var id : Int?,
        var name : String?,
        var region : String?,
        var job : String?,
        var company : String?,
        var field : String?,
        var status : String?,
        var image : String?,
        var coworkingEnabled : Boolean?,
        var keywords : ArrayList<String?>
)