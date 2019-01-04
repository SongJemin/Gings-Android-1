package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherInformResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherIntroResponse
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

    @Multipart
    @POST(" /reboards")
    fun postReBoard(
            @Header("Authorization") Authorization : String,
            @Part("content") summary : RequestBody,
            @Part images : ArrayList<MultipartBody.Part?>
    ) : Call<PostBoardResponse>


    @POST("/login")
    fun postLoginResponse(
            @Header("Content-type") content_type: String,
            @Body() body : JsonObject
    ) : Call<PostLogInResponse>

    @GET("/mypage/others/{myPageUserId}")
    fun getOtherPageInform(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId : Int?
    ) : Call<GetOtherInformResponse>

    @GET("/mypage/others/introduce/{myPageUserId}")
    fun getOtherPageIntro(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId : Int?
    ) : Call<GetOtherIntroResponse>

}
