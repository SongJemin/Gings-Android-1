package com.computer.inu.myworkinggings.Jemin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Jemin.POST.PostBoardResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class MypageUpdateActivity : AppCompatActivity() {
    lateinit var networkService : NetworkService
    private var keywords : java.util.ArrayList<RequestBody> = java.util.ArrayList()
    private var images : ArrayList<MultipartBody.Part?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_update)
    }

    // 보드 데이터 삽입
    fun postBoard() {
        networkService = ApplicationController.instance.networkService

        val title = RequestBody.create(MediaType.parse("text.plain"), "제목")
        val content = RequestBody.create(MediaType.parse("text.plain"), "ㅎㅇ")
        val category = RequestBody.create(MediaType.parse("text.plain"),"영감" )
        keywords.add(RequestBody.create(MediaType.parse("text.plain"), "기획"))
        //images.add(MultipartBody.Part.createFormData("img", img.name, photoBody))

        val postRoomTestResponse = networkService.postBoard("토큰",title, content,category,images, keywords)
        postRoomTestResponse.enqueue(object : retrofit2.Callback<PostBoardResponse>{

            override fun onResponse(call: Call<PostBoardResponse>, response: Response<PostBoardResponse>) {
                if(response.isSuccessful){

                }
                else{
                }
            }

            override fun onFailure(call: Call<PostBoardResponse>, t: Throwable?) {
            }
        })
    }

}
