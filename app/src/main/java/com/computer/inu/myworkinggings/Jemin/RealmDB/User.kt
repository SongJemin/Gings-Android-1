package com.computer.inu.myworkinggings.Jemin.RealmDB

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject(){
    @PrimaryKey
    var userID_roomID : Int = 0
    var id : Int = 0
    var name : String = ""
    var job : String = ""
    var image : String = ""
    var roomID : Int = 0
}