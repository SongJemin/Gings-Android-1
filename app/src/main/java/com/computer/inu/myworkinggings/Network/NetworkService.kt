package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Seunghee.Post.PostLogInResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    @POST("/login")
    fun postLoginResponse(
            @Header("Content-type") content_type: String,
            @Body() body : JsonObject
    ) : Call<PostLogInResponse>
}
