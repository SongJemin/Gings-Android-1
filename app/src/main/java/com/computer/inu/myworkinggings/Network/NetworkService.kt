package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Hyunjin.Get.GetSearchClub
import com.computer.inu.myworkinggings.Hyunjin.Get.GetVerifyNumberRequest
import com.computer.inu.myworkinggings.Hyunjin.Post.PostClubSignUp
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import com.computer.inu.myworkinggings.Jemin.POST.PostBoardResponse
import com.computer.inu.myworkinggings.Seunghee.Post.PostLogInResponse
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            @Header("Authorization") Authorization : String,
            @Query("offset") offset : Int?,
            @Query("limit") limit : Int?
    ) : Call<GetBoardResponse>

    @GET("/signup/authNumber")
    fun getVerifyNumberData(
            @Header("Authorization") Authorization : String,
            @Query("email") email : String
    ) : Call<GetVerifyNumberRequest>

    @GET("/clubs")
    fun getSearchClub(
            @Header("Authorization") Authorization : String
    ) : Call<GetSearchClub>

    @Multipart
    @POST("/boards")
    fun postBoard(
            @Header("Authorization") Authorization : String,
            @Part("title") title : RequestBody,
            @Part("content") summary : RequestBody,
            @Part("category") area: RequestBody,
            @Part images : ArrayList<MultipartBody.Part?>,
            @Part("keywords") keywords : ArrayList<RequestBody>
    ) : Call<PostBoardResponse>

    @POST("/login")
    fun postLoginResponse(
            @Header("Content-type") content_type: String,
            @Body() body : JsonObject
    ) : Call<PostLogInResponse>

    @POST("/clubs/{clubId}/join")
    fun postClubSignUp(
            @Header("Authorization") Authorization : String
    ) : Call<PostClubSignUp>
}