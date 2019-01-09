package com.computer.inu.myworkinggings.Hyunjin.Get

data class EventDetailSearch(
    var date : String,
    var time : String?,
    var title : String,
    var limit : Int,
    var place : String,
    var price : String,
    var eventImg : String,
    var detailImg : String,
    var eventStatus : String
)