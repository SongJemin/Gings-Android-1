package com.computer.inu.myworkinggings.Moohyeon.Activity

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
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DetailBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_detail_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

import android.widget.TextView
import com.computer.inu.myworkinggings.Jemin.Adapter.BoardImageAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ImageType
import com.computer.inu.myworkinggings.Seunghee.GET.DetailedBoardData
import com.computer.inu.myworkinggings.Seunghee.GET.GetDetailedBoardResponse
import com.computer.inu.myworkinggings.Seunghee.GET.ReplyData
import retrofit2.Callback

class DetailBoardActivity : AppCompatActivity() {
    lateinit var  detailBoardRecyclerViewAdapter : DetailBoardRecyclerViewAdapter
    var reboardImagesList : java.util.ArrayList<MultipartBody.Part?> = java.util.ArrayList()
    var reboardImageUrlList = java.util.ArrayList<ImageType>()
    var boardId : Int = 0
    var urlSize : Int = 0
    lateinit var boardImageAdapter : BoardImageAdapter
    lateinit var requestManager : RequestManager
    var modifyFlag : Int = 0
    lateinit var temp: DetailedBoardData
    var reboardId : Int = 0
    var seletectedPostion : Int = 0
    var deleteImagesUrl = ArrayList<String>()
    var prevImagesUrl = ArrayList<RequestBody>()

    val networkService: com.computer.inu.myworkinggings.Network.NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_board)
        detailBoardActivity = this
        getDetailedBoardResponse(0)

        boardId = intent.getIntExtra("BoardId", 0)
        requestManager = Glide.with(this)
        detail_board_reboard_btn.visibility = View.VISIBLE
        detail_board_reboard_modify_btn.visibility = View.GONE
        Log.v("asdf","전송 받은 보드 ID = " + boardId)
        //postReBoard()
        detail_board_reboard_img_recyclerview.visibility = View.GONE
        //setRecyclerView()

        detail_board_reboard_modify_btn.setOnClickListener {
            updateReBoard()
        }

        detail_board_reboard_img_btn.setOnClickListener {
            val tedBottomPicker = TedBottomPicker.Builder(this@DetailBoardActivity)
                    .setOnMultiImageSelectedListener {
                        reboardUriList: java.util.ArrayList<Uri>? ->
                        for(i in 0 .. reboardUriList!!.size-1){

                            if(temp.replys[seletectedPostion]!!.images.size > 0){
                                deleteImagesUrl = boardImageAdapter.deleteImageUrlList
                                if(deleteImagesUrl.size > 0){
                                    Log.v("asdf"," 지운 사진 = " +deleteImagesUrl[0])
                                }
                                else{
                                    Log.v("asdf","지운 사진 0")
                                }
                            }

                            urlSize = reboardUriList!!.size-1
                            reboardUriList!!.add(reboardUriList.get(i))
                            reboardImageUrlList.add(ImageType("null",reboardUriList.get(i)))

                            val options = BitmapFactory.Options()

                            var input: InputStream? = null // here, you need to get your context.


                            input = contentResolver.openInputStream(reboardImageUrlList.get(i).imageUri)
                            val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                            if(modifyFlag == 0){
                                Log.v("Adsf","이미지 선택 수정 아닐 시")
                                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                                val images = File(this.reboardImageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                                reboardImagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))
                            }
                            else{
                                Log.v("Adsf","이미지 선택 수정 시")
                                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                                val postImages = File(this.reboardImageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                                reboardImagesList.add(MultipartBody.Part.createFormData("postImages", postImages.name, photoBody))
                            }

                            if(reboardImageUrlList.size > 0){
                                detail_board_reboard_img_recyclerview.visibility = View.VISIBLE
                                boardImageAdapter = BoardImageAdapter(reboardImageUrlList, requestManager,2,1,0)
                                detail_board_reboard_img_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                                detail_board_reboard_img_recyclerview.adapter = boardImageAdapter
                            }
                            else{
                                detail_board_reboard_img_recyclerview.visibility = View.GONE
                            }
                        }
                    }
                    .setSelectMaxCount(1)
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

    private fun getDetailedBoardResponse(modifyFlag : Int) {
        var insertBoardID : Int
        // 수정X
        if(modifyFlag == 0){
            insertBoardID = intent.getIntExtra("BoardId", 0).toInt()
        }
        // 수정O
        else{
            insertBoardID = boardId
        }
        val getDetailedBoardResponse = networkService.getDetailedBoardResponse("application/json", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", insertBoardID)

        getDetailedBoardResponse.enqueue(object : Callback<GetDetailedBoardResponse> {
            override fun onFailure(call: Call<GetDetailedBoardResponse>, t: Throwable) {
                Log.e("detailed_board fail", t.toString())
            }

            override fun onResponse(call: Call<GetDetailedBoardResponse>, response: Response<GetDetailedBoardResponse>) {
                if (response.isSuccessful) {

                    //toast(intent.getIntExtra("BoardId",0))
                    Log.v("ggg", "board list success")
                    //Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()

                    //보드연결
                    temp = response.body()!!.data
                    bindBoardData(temp)

                    //리보드연결
                    val reboardtemp : ArrayList<ReplyData?> = response.body()!!.data.replys
                    bindReBoardData(reboardtemp)
                }
            }

        })
    }

    private fun bindBoardData(temp: DetailedBoardData) {

        /*타이틀*/
        tv_detail_board_time.text = temp.time!!.substring(0,16).replace("T"," ")
        tv_detail_board_category.text = temp.category
        tv_detail_board_title.text = temp.title
        Log.v("at","프사url = " + temp.writerImage)
        requestManager = Glide.with(this)
        requestManager.load(temp.writerImage).into(iv_item_board_profile_img)

        for(i in 0.. temp.keywords.size-1){
            if(i==0){
                tv_detail_board_tag.text = "#" + temp.keywords
            }
            else{
                tv_detail_board_tag.append("   #" + temp.keywords[i])
            }
        }

        /*contents*/
        //텍스트
        tv_detail_board_contents_text.text = temp.content
        //이미지

        for(i in 0..temp.images.size-1 )
            requestManager.load(temp.images[0]).centerCrop().into(iv_detail_board_contents_image)
        /* profile */
        //개인정보
        tv_item_board_profile_name.text = temp.writer
        tv_item_board_profile_role.text = temp.field
        tv_item_board_profile_team.text = temp.company

        //추천&댓글
        tv_item_board_like_cnt.text=temp.recommender.toString()
        tv_item_board_comment_cnt.text = temp.numOfReply.toString()

        //tv_item_board_profile_role.text = temp.
        //tv_item_board_profile_team.text = temp.
    }

    private fun bindReBoardData(temp : ArrayList<ReplyData?> ){

        detailBoardRecyclerViewAdapter = DetailBoardRecyclerViewAdapter(this, temp)
        detail_board_reboard_recyclerview.adapter = detailBoardRecyclerViewAdapter
        detail_board_reboard_recyclerview.layoutManager = LinearLayoutManager(this)
        detail_board_reboard_recyclerview.canScrollVertically(0)
        detailBoardRecyclerViewAdapter.notifyDataSetChanged()

    }

    fun postReBoard() {

        var token : String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        var networkService = ApplicationController.instance.networkService
        val boardId = RequestBody.create(MediaType.parse("text.plain"), "193")
        val content = RequestBody.create(MediaType.parse("text.plain"), detail_board_reboard_edit.text.toString())

        val postReBoardResponse = networkService.postReBoard(token,boardId, content, reboardImagesList)

        Log.v("TAG", "프로젝트 생성 전송 : 토큰 = " + token + ", 내용 = " + detail_board_reboard_edit.text.toString())

        postReBoardResponse.enqueue(object : retrofit2.Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "보드 값 전달 성공")
                    Log.v("TAG","보드 status = " + response.body()!!.status)
                    Log.v("TAG","보드 message = " + response.body()!!.message)
                    getDetailedBoardResponse(0)

                } else{
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateReBoard() {

        var token : String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        var networkService = ApplicationController.instance.networkService
        val content = RequestBody.create(MediaType.parse("text.plain"), detail_board_reboard_edit.text.toString())

        if(deleteImagesUrl.size > 0){
            prevImagesUrl.add(RequestBody.create(MediaType.parse("text.plain"), deleteImagesUrl[0]))
        }

        val postReBoardResponse = networkService.updateReBoard(token,reboardId, content , prevImagesUrl, reboardImagesList)

        postReBoardResponse.enqueue(object : retrofit2.Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "리보드 수정 값 전달 성공")
                    Log.v("TAG","리보드 수정 응답 status = " + response.body()!!.status)
                    Log.v("TAG","리보드 수정 응답 message = " + response.body()!!.message)
                    detail_board_reboard_img_recyclerview.visibility = View.GONE
                    reboardImageUrlList.clear()
                    detail_board_reboard_edit.setText("")
                    getDetailedBoardResponse(0)

                } else{
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        detailBoardRecyclerViewAdapter.onActivityResult(requestCode, resultCode, data!!)
        if(requestCode == 30){
            modifyFlag = 1
            reboardId = data.getIntExtra("reboardId", 0)
            detail_board_reboard_btn.visibility = View.GONE
            detail_board_reboard_modify_btn.visibility = View.VISIBLE
            getDetailedBoardResponse(1)
            for(i in 0 .. temp.replys.size-1){
                if(temp.replys[i]!!.replyId == reboardId){
                    detail_board_reboard_edit.setText(temp.replys[i]!!.content)
                    seletectedPostion = i
                }
            }
            reboardImageUrlList.clear()
            if(temp.images.size == 0){
                detail_board_reboard_img_recyclerview.visibility = View.VISIBLE
            }
            else{
                detail_board_reboard_img_recyclerview.visibility = View.VISIBLE
                reboardImageUrlList.add(ImageType(temp.replys[seletectedPostion]!!.images[0],null))
                boardImageAdapter = BoardImageAdapter(reboardImageUrlList, requestManager,2,0,1)
                detail_board_reboard_img_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                detail_board_reboard_img_recyclerview.adapter = boardImageAdapter
            }
        }
    }

    companion object {
        lateinit var detailBoardActivity: DetailBoardActivity
    }

}