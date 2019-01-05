package com.computer.inu.myworkinggings.Network

import com.computer.inu.myworkinggings.Jemin.Get.Response.*
import com.computer.inu.myworkinggings.Jemin.POST.PostBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetGuestBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageResponse
import com.computer.inu.myworkinggings.Moohyeon.post.PostBoardLikeResponse
import com.computer.inu.myworkinggings.Moohyeon.post.PostSignUpResponse
import com.computer.inu.myworkinggings.Seunghee.GET.GetDetailedBoardResponse
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
            @Query("email") email: String
    ): Call<GetEmailRedundancyResponse>

    @GET("/boards")
    fun getBoard(
            @Header("Authorization") Authorization: String,
            @Query("offset") offset: Int?,
            @Query("limit") limit: Int?
    ): Call<GetBoardResponse>

    @Multipart
    @POST("/boards")
    fun postBoard(
            @Header("Authorization") Authorization: String,
            @Part("title") title: RequestBody,
            @Part("content") summary: RequestBody,
            @Part("category") area: RequestBody,
            @Part images: ArrayList<MultipartBody.Part?>,
            @Part("keywords") keywords: ArrayList<RequestBody>
    ): Call<PostBoardResponse>

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
            @Body() body: JsonObject
    ): Call<PostLogInResponse>

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

    @POST("boards/{boardId}/recommend")
    fun postBoardLikeResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId : Int
    ) : Call<PostBoardLikeResponse>
    @POST("/signup")
    fun postSignUpResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Body() body : JsonObject
    ) : Call<PostSignUpResponse>

    @GET("/mypage/mine/guestboard")
    fun getGuestBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization : String
    ) : Call<GetGuestBoardResponse>

    @GET("/mypage/mine")
    fun getMypageResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization : String
    ) : Call<GetMypageResponse>

    @GET("/boards/{boardId}")
    fun getDetailedBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization  : String,
            @Path("boardId") boardId : Int
    ) : Call<GetDetailedBoardResponse>

    @GET("/mypage/others/guestboard/{myPageUserId}")
    fun getOtherGuestBoard(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId : Int
    ) : Call<GetOtherGuestBoardResponse>
}
