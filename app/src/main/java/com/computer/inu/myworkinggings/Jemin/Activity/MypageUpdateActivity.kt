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
import com.computer.inu.myworkinggings.Jemin.Data.ImageType
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetMyIntroduceResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetProfileImgUrlResponse
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_detail_board.*
import kotlinx.android.synthetic.main.activity_mypage_update.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.ctx
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.ArrayList

class MypageUpdateActivity : AppCompatActivity() {
    var imageUrlList = ArrayList<ImageType>()
    lateinit var boardImageAdapter : BoardImageAdapter
    lateinit var requestManager : RequestManager
    var imgs = ArrayList<String>()
    var getImageUrlSize : Int = 0
    var deleteImagesUrl = ArrayList<String>()
    var getServerImageUrl : Boolean = false
    var postImagesList: ArrayList<MultipartBody.Part?> = ArrayList()
    var prevImagesUrl = ArrayList<RequestBody>()
    var getServerData : Boolean = false
    
    var TAG = "MypageUdateActivity"

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_update)
        mypageUpdateActivity = this

        requestManager = Glide.with(this)

        getMyIntroduce()
        mypage_update_galley_btn.setOnClickListener {
            val tedBottomPicker = TedBottomPicker.Builder(this@MypageUpdateActivity)
                    .setOnMultiImageSelectedListener {
                        uriList: ArrayList<Uri>? ->

                        for (i in 0..uriList!!.size - 1) {
                            if(getImageUrlSize > 0){
                                deleteImagesUrl.addAll(boardImageAdapter.deleteImageUrlList)
                                if(boardImageAdapter.urlRemovedCount > 0){
                                    // 서버로부터 받은 이미지리스트 갱신 = 서버로부터 받아온 이미지 리스트 개수 - 어댑터에서 제거한 사진 개수
                                    getImageUrlSize = getImageUrlSize - boardImageAdapter.urlRemovedCount
                                    // 어댑터에서 제거한 사진 개수 초기화
                                    boardImageAdapter.urlRemovedCount = 0
                                }
                            }
                            else{
                                boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 1, getImageUrlSize)
                            }
                            imageUrlList.add(ImageType("null",uriList.get(i)))

                            val options = BitmapFactory.Options()
                            var input: InputStream? = null // here, you need to get your context.

                            // 이미 리사이클러뷰에 사진 존재할 경우 이미지 추가
                            if(getServerImageUrl == true){
                                input = contentResolver.openInputStream(imageUrlList.get(getImageUrlSize+i).imageUri)
                            }
                            else{
                                input = contentResolver.openInputStream(imageUrlList.get(i).imageUri)
                            }

                            if(getServerImageUrl == true){
                                Log.v(TAG,"수정 이미지 저장")
                                val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                                val baos = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                                val images = File(this.imageUrlList.get(i).imageUri.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                                //val images = File(this.imageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                                postImagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))
                            }
                            else{
                                Log.v(TAG,"최초 이미지 저장")
                                val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                                val baos = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                                val images = File(this.imageUrlList.get(i).imageUri.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                                //val images = File(this.imageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                                postImagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))
                            }
                            if (postImagesList.size > 0) {
                                mypage_update_recyclerview.visibility = View.VISIBLE

                            } else {
                                mypage_update_recyclerview.visibility = View.GONE
                            }

                        }

                        boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,1, 1, getImageUrlSize)

                        mypage_update_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                        mypage_update_recyclerview.adapter = boardImageAdapter
                    }
                    .setSelectMaxCount(3)
                    .showCameraTile(false)
                    .setPeekHeight(800)
                    .showTitle(false)
                    .setEmptySelectionText("선택된게 없습니다! 이미지를 선택해 주세요!")
                    .create()

            tedBottomPicker.show(supportFragmentManager)
        }

        mypage_update_confirm_btn.setOnClickListener {
            if (mypage_update_content_edit.text.toString() == "" || (postImagesList.size == 0 && getImageUrlSize == 0)) {
                Log.v("MyPageUpdate", "로그값 확인")
                if (postImagesList.size == 0 && getImageUrlSize == 0) {
                    Toast.makeText(applicationContext, "이미지를 넣어주세요", Toast.LENGTH_LONG).show()
                } else if (mypage_update_content_edit.text.toString() == "") {
                    Toast.makeText(applicationContext, "내용을 적어주세요", Toast.LENGTH_LONG).show()
                }

            }
            else{
                if (getServerData != true) {
                    Log.v(TAG, "최초 등록 준비 완료")
                    postMyIntroduce(0)
                } else {
                    Log.v(TAG, "수정 등록 준비 완료")
                    postMyIntroduce(1)
                }
            }


        }
    }

    fun postMyIntroduce(modifyFlag : Int) {

        val content = RequestBody.create(MediaType.parse("text.plain"), mypage_update_content_edit.text.toString())
        var postBoardResponse : Call<PostResponse>
        if(modifyFlag == 0){
            postBoardResponse = networkService.postMyIntroduce(SharedPreferenceController.getAuthorization(this), content, postImagesList)
        }
        else{
            for(i in 0 .. deleteImagesUrl.size-1){
                prevImagesUrl.add(RequestBody.create(MediaType.parse("text.plain"), deleteImagesUrl[i]))
            }
            postBoardResponse = networkService.updateMyIntroduce(SharedPreferenceController.getAuthorization(this), content, postImagesList, prevImagesUrl)
        }

        postBoardResponse.enqueue(object : retrofit2.Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v(TAG,"토큰 = " + SharedPreferenceController.getAuthorization(ctx))
                Log.v(TAG, "내용 = " + content)
                Log.v(TAG, "이전 이미지 리스트 = " + prevImagesUrl.size)
                Log.v(TAG, "추가 이미지 리스트 = " + postImagesList.size)
                Log.v(TAG, "통신 성공")
                if(response.isSuccessful){
                    Toast.makeText(applicationContext,"값 전달 성공" ,Toast.LENGTH_LONG).show()
                    Log.v(TAG, "소개 값 전달 성공")
                    Log.v(TAG,"소개 응답 status = " + response.body()!!.status)
                    Log.v(TAG,"소개 응답 message = " + response.body()!!.message)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Log.v(TAG, "소개 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMyIntroduce() {

        val getMyIntroduceResponse = networkService.getMyIntroduce(SharedPreferenceController.getAuthorization(this))
        getMyIntroduceResponse.enqueue(object : retrofit2.Callback<GetMyIntroduceResponse>{

            override fun onResponse(call: Call<GetMyIntroduceResponse>, response: Response<GetMyIntroduceResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.data != null){
                        Log.v(TAG, "수정 버튼 활성화")
                        mypage_update_title_tv.text = "자기소개 수정"
                        getServerData = true
                        imgs = response.body()!!.data[0].imgs!!
                        Log.v(TAG, "받은 데이터 = " + response.body()!!.data[0])
                        mypage_update_content_edit.hint = response.body()!!.data[0].content
                        boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 1, getImageUrlSize)

                        if(imgs.size > 0){
                            getServerImageUrl = true
                            getImageUrlSize = imgs.size - boardImageAdapter.urlRemovedCount
                        }
                        for(i in 0 .. imgs.size-1){
                            imageUrlList.add(ImageType(imgs[i],null))
                        }

                        mypage_update_recyclerview.visibility = View.VISIBLE
                        boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,1, 0, getImageUrlSize)
                        mypage_update_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                        mypage_update_recyclerview.adapter = boardImageAdapter
                    }
                    else{
                        Log.v(TAG, "최초 등록 버튼 활성화")
                        mypage_update_title_tv.text = "자기소개 등록"
                    }


                }
                else{
                }
            }

            override fun onFailure(call: Call<GetMyIntroduceResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"자기 소개 조회 서버 연결 실패", Toast.LENGTH_SHORT).show()
                Log.v(TAG,"실패 이유 = " + t.toString())
            }
        })
    }

    companion object {
        lateinit var mypageUpdateActivity : MypageUpdateActivity
    }
        
}
