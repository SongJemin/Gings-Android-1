package com.computer.inu.myworkinggings.RealmDB

import com.computer.inu.myworkinggings.Jemin.Data.ChatMessage
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChatRoom : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var chatMessage : ChatMessage? = null
}