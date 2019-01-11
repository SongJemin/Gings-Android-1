package com.computer.inu.myworkinggings.Network

import android.app.ListActivity
import com.computer.inu.myworkinggings.Hyunjin.Get.GetDetailSearchClub
import com.computer.inu.myworkinggings.Hyunjin.Get.GetEventSearch
import com.computer.inu.myworkinggings.Jemin.Get.Response.*
import com.computer.inu.myworkinggings.Jemin.POST.PostKeywords
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Hyunjin.Get.GetSearchClub
import com.computer.inu.myworkinggings.Hyunjin.Get.GetVerifyNumberRequest
import com.computer.inu.myworkinggings.Hyunjin.Post.PostClubSignUp
import com.computer.inu.myworkinggings.Hyunjin.Post.PostJoinEvent
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherInformResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherIntroResponse
import com.computer.inu.myworkinggings.Moohyeon.get.*
import com.computer.inu.myworkinggings.Moohyeon.post.PostBoardLikeResponse
import com.computer.inu.myworkinggings.Moohyeon.post.PostSignUpResponse
import com.computer.inu.myworkinggings.Seunghee.GET.*
import com.computer.inu.myworkinggings.Seunghee.Post.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
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

    @GET("/signup/authNumber")
    fun getVerifyNumberData(
            @Query("email") email: String
    ): Call<GetVerifyNumberRequest>

    @GET("/clubs")
    fun getSearchClub(
            @Header("Authorization") Authorization: String
    ): Call<GetSearchClub>

    @Multipart
    @POST("/boards")
    fun postBoard(
            @Header("Authorization") Authorization: String,
            @Part("title") title: RequestBody,
            @Part("content") content: RequestBody,
            @Part("category") category: RequestBody,
            @Part images: ArrayList<MultipartBody.Part?>,
            @Part("keywords") keywords: ArrayList<RequestBody>
    ): Call<PostResponse>

    @Multipart
    @PUT("/boards/{boardId}")
    fun updateBoard(
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId : Int,
            @Part("title") title: RequestBody,
            @Part("content") content : RequestBody,
            @Part("category") category: RequestBody,
            @Part("prevImagesUrl") prevImagesUrl : ArrayList<RequestBody>,
            @Part postImages: ArrayList<MultipartBody.Part?>,
            @Part("prevKeywords") prevKeywords: ArrayList<RequestBody>,
            @Part("postKeywords") postKeywords: ArrayList<RequestBody>
    ): Call<PostResponse>

    @Multipart
    @POST(" /reboards")
    fun postReBoard(
            @Header("Authorization") Authorization: String,
            @Part("boardId") boardId : RequestBody,
            @Part("content") summary: RequestBody,
            @Part images: ArrayList<MultipartBody.Part?>
    ): Call<PostResponse>

    @Multipart
    @PUT("/reboards/{reboardId}")
    fun updateReBoard(
            @Header("Authorization") Authorization: String,
            @Path("reboardId") reboardId : Int,
            @Part("content") content : RequestBody,
            @Part("prevImagesUrl") prevImagesUrl: ArrayList<RequestBody>,
            @Part postImages : ArrayList<MultipartBody.Part?>
    ): Call<PostResponse>


    @POST("/login")
    fun postLoginResponse(
            @Header("Content-type") content_type: String,
            @Body() body: JsonObject
    ): Call<PostLogInResponse>

    @GET("/mypage/others/{myPageUserId}")
    fun getOtherPageInform(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId: Int?
    ): Call<GetOtherInformResponse>

    @GET("/mypage/others/introduce/{myPageUserId}")
    fun getOtherPageIntro(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId: Int?
    ): Call<GetOtherIntroResponse>

    @POST("boards/{boardId}/recommend")
    fun postBoardLikeResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId: Int?
    ): Call<PostBoardLikeResponse>

    @POST("/signup")
    fun postSignUpResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Body() body: JsonObject
    ): Call<PostSignUpResponse>

    @GET("/mypage/mine/guestboard")
    fun getGuestBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String
    ): Call<GetGuestBoardResponse>

    @GET("/mypage/mine")
    fun getMypageResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String
    ): Call<GetMypageResponse>

    @GET("/boards/{boardId}")
    fun getDetailedBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization: String,
            @Path("boardId") boardId: Int
    ): Call<GetDetailedBoardResponse>

    @GET("/mypage/others/guestboard/{myPageUserId}")
    fun getOtherGuestBoard(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId: Int
    ): Call<GetOtherGuestBoardResponse>

    @POST("/mypage/guestboard/{myPageUserId}")
    fun postOtherGuestBoard(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId: Int,
            @Body() body: JsonObject
    ): Call<PostResponse>


    @Multipart
    @POST("/mypage/setting/introduce")
    fun postMyIntroduce(
            @Header("Authorization") Authorization: String,
            @Part("content") content: RequestBody,
            @Part images: ArrayList<MultipartBody.Part?>
    ): Call<PostResponse>

    @Multipart
    @POST("/mypage/setting/introduce")
    fun updateMyIntroduce(
            @Header("Authorization") Authorization: String,
            @Part("content") content: RequestBody,
            @Part images: ArrayList<MultipartBody.Part?>,
            @Part ("prevImagesUrl") prevImagesUrl : ArrayList<RequestBody>
    ): Call<PostResponse>

    @GET("/mypage/others/active/{myPageUserId}")
    fun getOtherActive(
            @Header("Authorization") Authorization: String,
            @Path("myPageUserId") myPageUserId: Int
    ): Call<GetOtherActiveResponse>

    @GET("/mypage/setting/image")
    fun getProfileImgUrl(
            @Header("Authorization") Authorization: String
    ): Call<GetProfileImgUrlResponse>

    @Multipart
    @PUT("/mypage/setting/image")
    fun putMyProfileImg(
            @Header("Authorization") Authorization: String,
            @Part("image") image: RequestBody,
            @Part imgFile: MultipartBody.Part?
    ): Call<PostResponse>

    @PUT("/mypage/setting/info")
    fun putProfileInfo(
            @Header("Authorization") Authorization: String,
            @Body() body: JsonObject
    ): Call<PostResponse>

    @GET("/mypage/setting/introduce")
    fun getMyIntroduce(
            @Header("Authorization") Authorization: String
    ): Call<GetMyIntroduceResponse>

    @POST("/mypage/setting/info/keyword")
    fun postKeywordList(
            @Header("Authorization") Authorization: String,
            @Body postKeywords: PostKeywords
    ): Call<PostResponse>

    @GET("/mypage/mine/introduce")
    fun getMypageIntroduceResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization: String
    ): Call<GetMypageIntroduceResponse>

    @GET("/mypage/mine/active")
    fun getMypageActResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization: String
    ): Call<GetMypageActResponse>

    @Multipart
    @PUT("/boards/{boardId}")
    fun putModifyBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId: Int,
            @Part("title") title: RequestBody,
            @Part("content") content: RequestBody,
            @Part("category") category: RequestBody,
            @Part("prevImagesUrl") prevImagesURL: ArrayList<RequestBody?>,
            @Part postImages: ArrayList<MultipartBody.Part?>,
            @Part("prevKeywords") prevKeywords: ArrayList<String?>,
            @Part("postKeywords") postKeywords: ArrayList<String?>
    ): Call<PutModifyBoardResponse>

    @POST("reboards/{reboardId}/recommend")
    fun postReboardRecommendResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("reboardId") reboardId: Int
    ): Call<PostReboardRecommendResponse>

    @POST("/clubs/{clubId}/join")
    fun postClubSignUp(

            @Header("Authorization") Authorization : String,
            @Path("clubId") clubId : Int
    ) : Call<PostClubSignUp>

    @GET("/search/directory")
    fun getDirectorySearchResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization  : String,
            @Query("keyword") keyword : String
    ) : Call<GetDirectorySearchResponse>

    @GET("/search/directory/new")
    fun getDirectoryListResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") authorization  : String,
            @Query("offset") offset: Int?,
            @Query("limit") limit: Int?
    ) : Call<GetDirectoryListResponse>


    @DELETE("/boards/{boardId}")
    fun deleteBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId: Int
    ): Call<DeleteBoardResponse>

    @DELETE("/reboards/{reboardId}")
    fun deleteReboardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("reboardId") reboardId: Int
    ): Call<DeleteReboardResponse>

    @GET("boards/category/{category}/latest")
    fun getCategoryBoardResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("category") category: String
    ): Call<GetCategoryBoardResponse>

    @GET("/search/boards/latest")
    fun getBoardSearchResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Query("keyword") keyword: String
    ): Call<GetBoardSearchResponse>


    @POST("/mypage/setting/modifyPwd")
    fun postCurrentPasswordConfirm(
            @Header("Authorization") Authorization: String,
            @Body() body: JsonObject
    ): Call<PostResponse>

    @PATCH("/mypage/setting/modifyPwd")
    fun patchPassword(
            @Header("Authorization") Authorization: String,
            @Body() body: JsonObject
    ): Call<PostResponse>

    @GET("/clubs/{clubId}")
    fun getDetailSearchClub(
            @Header("Authorization") Authorization : String,
            @Path("clubId") clubId : Int
    ) : Call<GetDetailSearchClub>

    @GET("/clubs/{clubId}/{eventId}")
    fun getEventSearch(
            @Header("Authorization") Authorization : String,
            @Path("clubId") clubId : Int,
            @Path("eventId") eventId : Int
    ) : Call<GetEventSearch>

    @POST("/events/{eventId}")
    fun postJoinEvent(
            @Header("Authorization") Authorization : String,
            @Path("eventId") eventId: Int
    ) : Call<PostJoinEvent>

    @GET("boards/category/{category}/recommend")
    fun getCategoryLikeRankResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("category") category: String
    ) : Call<GetCategoryLikeRankResponse>

    @GET("/search/boards/category/{category}/latest")
    fun getCategorySearchResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("category") category: String,
            @Query("keyword") keyword: String
    ) : Call<GetCategorySearchResponse>

    @GET("/search/boards/category/{category}/recommend")
    fun getCategorySearchLikeRankResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("category") category: String,
            @Query("keyword") keyword: String
    ) : Call<GetCategorySearchLikeRankResponse>

    @POST("boards/{boardId}/share")
    fun postBoardShareResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId: Int
    ) : Call<PostBoardShareResponse>

    @POST("boards/{boardId}/block")
    fun postBlockBoardIDResponse(
            @Header("Content-type") content_type: String,
            @Header("Authorization") Authorization: String,
            @Path("boardId") boardId: Int
    ) : Call<PostBlockBoardIDResponse>
}
