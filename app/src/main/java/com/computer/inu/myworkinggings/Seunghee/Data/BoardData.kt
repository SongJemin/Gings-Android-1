package com.computer.inu.myworkinggings.data

data class BoardData(

        //titile
        var category: String,
        var title: String,
        var tag: String,
        var time: String,

        //contents
        //var contents_img: String,
        var contents_text: String,
        //var contents_more: Boolean,

        //profile
        //var profile_img: String,
        var name: String,
        var team: String,
        var role: String,

        //like,
        //var like : Boolean,
        var like_cnt: Int,

        //comment,
        // var comment : Boolean,
        var comment_cnt: Int

        //share
        //var share : Boolean,

        //more
        //var more : Boolean

)