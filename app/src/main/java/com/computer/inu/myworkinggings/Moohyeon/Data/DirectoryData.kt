package com.computer.inu.myworkinggings.Moohyeon.Data

data class DirectoryData (
        var id : Int?,
        var name : String?,
        var company : String?,
        var job : String?,
        var  field :String?,
        var  coworkingChk : Boolean?,
        var writeTime : String?,
        var  image : String?,
        var  introduce : ArrayList<IntroduceData>
)