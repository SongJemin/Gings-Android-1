package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Adapter.BoardImageAdapter
import com.computer.inu.myworkinggings.Jemin.Adapter.GetImageUrlAdapter
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetMyIntroduceResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetProfileImgUrlResponse
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_detail_board.*
import kotlinx.android.synthetic.main.activity_mypage_update.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.ArrayList

class MypageUpdateActivity : AppCompatActivity() {
    private var imagesList : ArrayList<MultipartBody.Part?> = ArrayList()
    var imageUrlList = ArrayList<Uri>()
    lateinit var boardImageAdapter : BoardImageAdapter
    var urlSize : Int = 0
    lateinit var requestManager : RequestManager
    lateinit var getImageUrlAdapter : GetImageUrlAdapter
    var imgs = ArrayList<String>()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_update)

        requestManager = Glide.with(this)
        getMyIntroduce()

        mypage_update_galley_btn.setOnClickListener {
            val tedBottomPicker = TedBottomPicker.Builder(this@MypageUpdateActivity)
                    .setOnMultiImageSelectedListener {
                        uriList: ArrayList<Uri>? ->
                        imageUrlList.clear()
                        for(i in 0 .. uriList!!.size-1){
                            urlSize = uriList!!.size-1
                            uriList!!.add(uriList.get(i))

                            imageUrlList.add(uriList.get(i))

                            val options = BitmapFactory.Options()

                            var input: InputStream? = null // here, you need to get your context.


                            input = contentResolver.openInputStream(imageUrlList.get(i))
                            val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                            val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                            val images = File(this.imageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                            imagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))

                            for(i in 0 .. imagesList.size-1){
                            }
                            if(imageUrlList.size > 0){
                                mypage_update_recyclerview.visibility = View.VISIBLE
                                boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager)
                                mypage_update_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                                mypage_update_recyclerview.adapter = boardImageAdapter
                            }
                            else{
                                mypage_update_recyclerview.visibility = View.GONE
                            }

                        }

                    }
                    .setSelectMaxCount(4)
                    .showCameraTile(false)
                    .setPeekHeight(800)
                    .showTitle(false)
                    .setEmptySelectionText("선택된게 없습니다! 이미지를 선택해 주세요!")
                    .create()

            tedBottomPicker.show(supportFragmentManager)
        }

        mypage_update_confirm_tv.setOnClickListener {
            postMyIntroduce()
        }

    }

    fun postMyIntroduce() {

        val content = RequestBody.create(MediaType.parse("text.plain"), "ㅎㅇㅎㅇ")
        val postBoardResponse = networkService.postMyIntroduce("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", content, imagesList)

        postBoardResponse.enqueue(object : retrofit2.Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "보드 값 전달 성공")
                    Log.v("TAG","보드 status = " + response.body()!!.status)
                    Log.v("TAG","보드 message = " + response.body()!!.message)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMyIntroduce() {

        val getMyIntroduceResponse = networkService.getMyIntroduce("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg")

        getMyIntroduceResponse.enqueue(object : retrofit2.Callback<GetMyIntroduceResponse>{

            override fun onResponse(call: Call<GetMyIntroduceResponse>, response: Response<GetMyIntroduceResponse>) {
                if(response.isSuccessful){
                    mypage_update_content_edit.hint = response.body()!!.data[0].content
                    imgs = response.body()!!.data[0].imgs

                    mypage_update_recyclerview.visibility = View.VISIBLE
                    getImageUrlAdapter = GetImageUrlAdapter(imgs, requestManager)
                    mypage_update_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    mypage_update_recyclerview.adapter = getImageUrlAdapter
                }
                else{
                }
            }

            override fun onFailure(call: Call<GetMyIntroduceResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"자기 소개 조회 서버 연결 실패", Toast.LENGTH_SHORT).show()
                Log.v("asdf","실패 이유 = " + t.toString())
            }

        })
    }

}
