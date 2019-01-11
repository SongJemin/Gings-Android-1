package com.computer.inu.myworkinggings.Jemin.Data
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by JAMCOM on 2018-05-26.
 */
open class PokemonVO : RealmObject() {
    @PrimaryKey
    var name : String = ""
    var num : Int = 0
    var type : String = ""
    var user : String = ""


}