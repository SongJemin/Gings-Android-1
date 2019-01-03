package com.computer.inu.myworkinggings.Seunghee.db

import android.content.Context

object SharedPreferenceController{
    private val USER_NAME = "MYKEY"
    private val myAuth = "myAuth"

    fun setAuthorization(context: Context, authorization : String){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(myAuth, authorization)
        editor.commit()
    }

    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        return pref.getString(myAuth, "")
    }

    fun clearSPC(context: Context){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}