package com.computer.inu.myworkinggings.data

data class BoardData(var category : String,
                     var title : String,
                     var tag : String,
                     var time : String,
                     //var contents_img : String,
                     var contents_text : String,
                     //var contents_more : Boolean,
                     //var profile_img : String,
                     var name : String,
                     var team : String,
                     var role : String,
                     //추천아이콘,
                     var like_cnt : Int,
                     //댓글아이콘,
                     var comment_cnt : Int
                     //공유하기
                     //더보기
                     )