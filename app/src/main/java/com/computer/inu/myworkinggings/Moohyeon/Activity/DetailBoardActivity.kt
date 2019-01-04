package com.computer.inu.myworkinggings.Moohyeon.Activity

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
import com.computer.inu.myworkinggings.Jemin.POST.PostBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DetailBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.ReBoardData
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_detail_board.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class DetailBoardActivity : AppCompatActivity() {
    lateinit var  detailBoardRecyclerViewAdapter : DetailBoardRecyclerViewAdapter
    private var reboardImagesList : java.util.ArrayList<MultipartBody.Part?> = java.util.ArrayList()
    var reboardImageUrlList = java.util.ArrayList<Uri>()
    var boardId : Int = 0
    var urlSize : Int = 0
    lateinit var boardImageAdapter : BoardImageAdapter
    lateinit var requestManager : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_board)

        boardId = intent.getIntExtra("boardId", 0)
        requestManager = Glide.with(this)
        Log.v("asdf","전송 받은 보드 ID = " + boardId)
        //postReBoard()
        detail_board_reboard_img_recyclerview.visibility = View.GONE
        setRecyclerView()

        detail_board_reboard_img_btn.setOnClickListener {
            val tedBottomPicker = TedBottomPicker.Builder(this@DetailBoardActivity)
                    .setOnMultiImageSelectedListener {
                        reboardUriList: java.util.ArrayList<Uri>? ->
                        reboardImageUrlList.clear()
                        for(i in 0 .. reboardUriList!!.size-1){
                            urlSize = reboardUriList!!.size-1
                            reboardUriList!!.add(reboardUriList.get(i))

                            reboardImageUrlList.add(reboardUriList.get(i))
                            Log.v("TAG","re이미지 = " + reboardUriList.get(i))

                            val options = BitmapFactory.Options()

                            var input: InputStream? = null // here, you need to get your context.


                            input = contentResolver.openInputStream(reboardImageUrlList.get(i))
                            val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                            val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                            val images = File(this.reboardImageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                            Log.v("asdf","re이미지3 = " + images)

                            Log.v("asdf","re이미지5 = " + images.name)
                            Log.v("asdf","re이미지6 = " + images.name.toString())
                            reboardImagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))

                            for(i in 0 .. reboardImagesList.size-1){

                                Log.v("asdf", "re이미지리스트 = " + images.toString())
                            }

                            if(reboardImageUrlList.size > 0){
                                detail_board_reboard_img_recyclerview.visibility = View.VISIBLE
                                boardImageAdapter = BoardImageAdapter(reboardImageUrlList, requestManager)
                                detail_board_reboard_img_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                                detail_board_reboard_img_recyclerview.adapter = boardImageAdapter
                            }
                            else{
                                detail_board_reboard_img_recyclerview.visibility = View.GONE
                            }

                            /*
                            try {
                                for(i in 0 .. urlSize){

                                }

                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            }
                            */

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

        detail_board_reboard_btn.setOnClickListener {
            if(detail_board_reboard_edit.text.toString() == ""){
                Toast.makeText(applicationContext,"내용 입력하세요.", Toast.LENGTH_LONG).show()
            }
            else{
                Log.v("asdf", "리보드 준비 완료" + detail_board_reboard_edit.text.toString())
                postReBoard()
            }
        }
    }

    private fun setRecyclerView() {
        //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입
        //임시데이터
        var dataList: ArrayList<ReBoardData> = ArrayList()
        dataList.add(ReBoardData("김무현", "3시간전", "안녕하세요!"))
        dataList.add(ReBoardData("김무현1", "1시간전", "ㅎㅇ"))
        dataList.add(ReBoardData("김무현2", "3시간전", "ㄱㄱ!"))
        dataList.add(ReBoardData("김무현3", "8시간전", "ㄱㄱ!"))
        dataList.add(ReBoardData("김무현4", "6시간전", "안녕하세요!"))
        dataList.add(ReBoardData("김무현5", "3시간전", "ㄱㄱ세요!"))
        dataList.add(ReBoardData("김무현6", "4시간전", "ㄱㄱㄱ!"))
        dataList.add(ReBoardData("김무현7", "5시간전", "안녕하세요!"))
        dataList.add(ReBoardData("김무현8", "6시간전", "ㅎㅎㅎㅎㅎㅎ!"))

        //BoardRecyclerViewAdapter = BoardRecyclerViewAdapter(this, dataList)
        detailBoardRecyclerViewAdapter = DetailBoardRecyclerViewAdapter(this, dataList)
        rv_item_detailboard_list.adapter = detailBoardRecyclerViewAdapter
        rv_item_detailboard_list.layoutManager = LinearLayoutManager(this)
        rv_item_detailboard_list.canScrollVertically(0)

    }

    fun postReBoard() {

        var token : String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        var networkService = ApplicationController.instance.networkService
        val content = RequestBody.create(MediaType.parse("text.plain"), detail_board_reboard_edit.text.toString())

        val postReBoardResponse = networkService.postReBoard(token, content, reboardImagesList)

        Log.v("TAG", "프로젝트 생성 전송 : 토큰 = " + token + ", 내용 = " + detail_board_reboard_edit.text.toString())

        postReBoardResponse.enqueue(object : retrofit2.Callback<PostBoardResponse>{

            override fun onResponse(call: Call<PostBoardResponse>, response: Response<PostBoardResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "보드 값 전달 성공")
                    Log.v("TAG","보드 status = " + response.body()!!.status)
                    Log.v("TAG","보드 message = " + response.body()!!.message)
                }
                else{
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostBoardResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

}