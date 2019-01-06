package com.computer.inu.myworkinggings.Moohyeon.Data

import android.provider.MediaStore

data class ReplysData (

       var replyId : Int?,
       var writerId : String?,
       var  content : String?,
       var  writeTime : Int?,
       var images: ArrayList<String>,
       var  recommender : Int

)