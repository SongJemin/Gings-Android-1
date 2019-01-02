package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    ////////////////////* GET *///////////////////////////

    @GET("/signup/email")
    fun getEmailRedundancyResponse(
            @Query("email") email : String
    ) : Call<GetEmailRedundancyResponse>

    @GET("/boards")
    fun getBoard(
            @Query("offset") offset : Int?,
            @Query("limit") limit : Int?
    ) : Call<GetBoardResponse>

}