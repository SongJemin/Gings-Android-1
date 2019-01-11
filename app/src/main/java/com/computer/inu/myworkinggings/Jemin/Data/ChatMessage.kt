package com.computer.inu.myworkinggings.Jemin.Data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChatMessage : RealmObject(){
    @PrimaryKey
    var id : Int = 0
    var roomId: Int = 0
    var writerId: Int = 0
    var writeAt: String = ""
}