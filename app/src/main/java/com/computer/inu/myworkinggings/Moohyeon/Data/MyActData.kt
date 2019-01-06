package com.computer.inu.myworkinggings.Moohyeon.Data

import android.media.Image

data class MyActData (
        var boardId : Int?,
        var  writerId : Int?,
        var  title : String?,
        var content : String?,
        var share : Int?,
        var  category : String?,
        var image : ArrayList<String>,
        var kewords : ArrayList<String>,
        var replys  : ArrayList<ReplysData>,
        var  recommender : Int
)