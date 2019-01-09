package com.computer.inu.myworkinggings.Hyunjin.Get

data class EventDetailData(
        val eventId : Int,
        val date : String,
        val time : String?,
        val title : String,
        val limit : Int,
        val price : String,
        val place : String,
        val eventImg : String,
        val detailImg : String,
        val eventStatus : String
)