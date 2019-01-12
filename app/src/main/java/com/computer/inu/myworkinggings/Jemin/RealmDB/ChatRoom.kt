package com.computer.inu.myworkinggings.Jemin.RealmDB

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChatRoom : RealmObject() {
    @PrimaryKey
    var id : Int = 0
    var type : String = ""
}