package com.computer.inu.myworkinggings.Jemin.Data

data class BoardItem (
        var boardId: Int?,
        var writerId: Int?,
        var writer: String?,
        var writerImage : String?,
        var field : String?,
        var company : String?,
        var title: String?,
        var content: String?,
        var share: Int?,
        var time: String?,
        var category: String?,
        var images: ArrayList<String>,
        var keywords: ArrayList<String>,
        var numOfReply: Int?,
        var recommender: Int?
)