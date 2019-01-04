package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Hyunjin.get.GetVerifyNumberRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkService {
    @GET("/signup/authNumber")
    fun getVerifyNumberData(
            @Header("token") token : String,
            @Query("email") email : String
            ) : Call<GetVerifyNumberRequest>
}