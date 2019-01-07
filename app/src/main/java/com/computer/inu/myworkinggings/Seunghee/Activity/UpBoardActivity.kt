package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import com.computer.inu.myworkinggings.Jemin.Adapter.BoardImageAdapter
import com.computer.inu.myworkinggings.Jemin.Adapter.GetImageUrlAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ImageType
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.AlbumRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.GET.DetailedBoardData
import com.computer.inu.myworkinggings.Seunghee.GET.GetDetailedBoardResponse
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_mypage_update.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class UpBoardActivity : AppCompatActivity() {

    lateinit var temp: DetailedBoardData
    lateinit var AlbumRecyclerViewAdapter: AlbumRecyclerViewAdapter
    var imagesList: ArrayList<MultipartBody.Part?> = ArrayList()
    var urlSize: Int = 0
    var getImageUrlSize : Int = 0
    var imageUrlList = ArrayList<ImageType>()
    private var keywords = ArrayList<RequestBody>()
    var selectedCategory: String = ""
    lateinit var requestManager: RequestManager
    lateinit var boardImageAdapter: BoardImageAdapter

    var getServerImageUrl : Boolean = false

    lateinit var categoryListText: Array<TextView>
    var prevKeywords = ArrayList<String>()
    var postKeywords = ArrayList<String>()
    var prevImagesUrl = ArrayList<String>()
    //lateinit var getImageUrlAdapter : GetImageUrlAdapter

    //private var postImages: MultipartBody.Part? = null
    private var postImages: MultipartBody.Part? = null
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_board)
        categoryListText = arrayOf(tv_up_board_question, tv_up_board_inspiration, tv_up_board_collaboration)
        upBoardActivity = this
            /*

            */


        upboard_pick_recyclerview.visibility = View.GONE
        /*카테고리 선택&재선택 함수*/
        categorySelectOnClickListener()

        /*사진선택*/
        //이미지 버튼 클릭시
        iv_upboard_input_image.setOnClickListener {
            val tedBottomPicker = TedBottomPicker.Builder(this@UpBoardActivity)
                    .setOnMultiImageSelectedListener { uriList: ArrayList<Uri>? ->
                        //imageUrlList.clear()

                        for (i in 0..uriList!!.size - 1) {

                            if(getImageUrlSize > 0){
                                prevImagesUrl.addAll(boardImageAdapter.deleteImageUrlList)
                                Log.v("Asdf","뺀 값 = " + boardImageAdapter.urlRemovedCount)
                                if(boardImageAdapter.urlRemovedCount > 0){
                                    // 서버로부터 받은 이미지리스트 갱신 = 서버로부터 받아온 이미지 리스트 개수 - 어댑터에서 제거한 사진 개수
                                    getImageUrlSize = getImageUrlSize - boardImageAdapter.urlRemovedCount
                                    Log.v("asdf","서버 사진 개수 초기화 = " + getImageUrlSize)
                                    // 어댑터에서 제거한 사진 개수 초기화
                                    boardImageAdapter.urlRemovedCount = 0
                                }
                            }
                            else{
                                boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 1, getImageUrlSize)
                            }

                            //uriList!!.add(uriList.get(i))
                            //urlSize = uriList!!.size - 1
                            imageUrlList.add(ImageType("null",uriList.get(i)))
                            Log.v("TAG", "이미지 = " + uriList.get(i))

                            val options = BitmapFactory.Options()

                            var input: InputStream? = null // here, you need to get your context.

                            // 이미 리사이클러뷰에 사진 존재할 경우 이미지 추가
                            if(getServerImageUrl == true){
                                Log.v("tag", "서버로부터 이미지 사진 받음")
                                Log.v("tag", "서버로부터 이미지 사진 받음 = " + getImageUrlSize)
                                input = contentResolver.openInputStream(imageUrlList.get(getImageUrlSize+i).imageUri)

                            }
                            else{
                                input = contentResolver.openInputStream(imageUrlList.get(i).imageUri)
                            }


                            val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                            val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                            val images = File(this.imageUrlList.get(i).imageUri.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                            //val images = File(this.imageUrlList.get(i).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                            imagesList.add(MultipartBody.Part.createFormData("images", images.name, photoBody))

                            for (i in 0..imagesList.size - 1) {

                            }

                            if (imageUrlList.size > 0) {
                                upboard_pick_recyclerview.visibility = View.VISIBLE

                            } else {
                                upboard_pick_recyclerview.visibility = View.GONE

                            }

                        }

                        boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 1, getImageUrlSize)

                        upboard_pick_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                        upboard_pick_recyclerview.adapter = boardImageAdapter

                    }
                    .setSelectMaxCount(4)
                    .showCameraTile(false)
                    .setPeekHeight(800)
                    .showTitle(false)
                    .setEmptySelectionText("선택된게 없습니다! 이미지를 선택해 주세요!")
                    .create()

            tedBottomPicker.show(supportFragmentManager)
        }

        requestManager = Glide.with(this)
        //수정으로 들어왔을 때
        val modifyBoardID = intent.getIntExtra("ModifyBoardID", -1)

        if (modifyBoardID > 0) {

            iv_upboard_confirm_tv.visibility = View.GONE
            iv_upboard_modify_tv.visibility = View.VISIBLE

            tv_up_board_upboard.visibility = View.GONE
            tv_up_board_modifyboard.visibility = View.VISIBLE

            //디테일보드 통신에서 정보 가져옴
            getDetailedBoardResponse(modifyBoardID)


        } else {
            iv_upboard_confirm_tv.visibility = View.VISIBLE
            iv_upboard_modify_tv.visibility = View.GONE

            tv_up_board_upboard.visibility = View.VISIBLE
            tv_up_board_modifyboard.visibility = View.GONE

        }

        iv_upboard_confirm_tv.setOnClickListener {
            /*
           Log.v("TAG", "이미지 URL리스트 총 크기 = " + imageUrlList.size)
           Log.v("TAG", "이미지 멀티파트 총 크기 = " + imagesList.size)

           for (i in 0 .. imageUrlList.size-1){
               Log.v("TAG", "이미지 객체 리스트 [" + i + "]번째 이미지 uri 값 = " + imageUrlList[i].imageUri)
           }
           for(i in 0 .. imagesList.size-1){
               Log.v("TAG", "이미지 멀티파트 리스트 [" + i + "]번째 이미지 멀티파트 값 = " + imagesList[i].toString())

           }
           */
            val keywordList = et_up_board_tags.text.toString().split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (keyword in keywordList) {
                Log.v("Asdf", "키워드 자르기 = " + keyword.replace("#", ""))
                keywords.add(RequestBody.create(MediaType.parse("text.plain"), keyword.replace("#", "")))
            }
            if (et_up_board_title.text.toString() == "" || et_up_board_modify.text.toString() == "" || selectedCategory == "" || imagesList.size == 0
                    || keywords.size == 0) {
                if (et_up_board_title.text.toString() == "") {
                    Log.v("ㅁㄴㅇㄹ", "제목 null값 들어감")
                } else if (et_up_board_modify.text.toString() == "") {
                    Log.v("ㅁㄴㅇㄹ", "내용 null값 들어감")
                } else if (selectedCategory == "") {
                    Log.v("ㅁㄴㅇㄹ", "카테고리null값 들어감")
                } else if (imagesList.size == 0) {
                    Log.v("ㅁㄴㅇㄹ", "이미지 사이즈 null값 들어감")
                } else if (keywords.size == 0) {
                    Log.v("ㅁㄴㅇㄹ", "키워드 null값 들어감")
                }
            } else {
                Log.v("asdf", "널값없이 잘 들어옴")
                postBoard()

            }
        }
        iv_upboard_modify_tv.setOnClickListener {
            val updatekeywordList = et_up_board_tags.text.toString().split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()


            for (keyword in updatekeywordList) {
                Log.v("Asdf", "키워드 자르기 = " + keyword.replace("#", ""))
                postKeywords.add(keyword.replace("#", ""))
            }

            for(i in 0 .. temp.keywords.size-1){
                Log.v("asdf","검사 [" + i + "] 번째 키워드 = " + temp.keywords[i])
                if(postKeywords.contains(temp.keywords[i])){
                    Log.v("asdf","해당 키워드 다시 등장")
                    postKeywords.remove(temp.keywords[i])
                }
                else{
                    Log.v("asdf","해당 키워드 삭제")
                    prevKeywords.add(temp.keywords[i]!!)
                }
            }
            updateBoard()
            /*

            if (et_up_board_title.text.toString() == "" || et_up_board_modify.text.toString() == "" || selectedCategory == "" || imagesList.size == 0
                    || keywords.size == 0) {
                if (et_up_board_title.text.toString() == "") {
                    Log.v("ㅁㄴㅇㄹ", "제목 null값 들어감")
                } else if (et_up_board_modify.text.toString() == "") {
                    Log.v("ㅁㄴㅇㄹ", "내용 null값 들어감")
                } else if (selectedCategory == "") {
                    Log.v("ㅁㄴㅇㄹ", "카테고리null값 들어감")
                } else if (imagesList.size == 0) {
                    Log.v("ㅁㄴㅇㄹ", "이미지 사이즈 null값 들어감")
                } else if (keywords.size == 0) {
                    Log.v("ㅁㄴㅇㄹ", "키워드 null값 들어감")
                }
            } else {
                Log.v("asdf", "널값없이 잘 들어옴")
                Toast.makeText(applicationContext, "수정 준비 완료",Toast.LENGTH_LONG).show()
            }
        */
        }

    }

    //디테일보드 통신
    private fun getDetailedBoardResponse(modifyBoardID: Int) {
        Log.v("vdvd","숫자2 = " + modifyBoardID)

        //toast("토오스트")
        val getDetailedBoardResponse = networkService.getDetailedBoardResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                modifyBoardID)

        getDetailedBoardResponse.enqueue(object : Callback<GetDetailedBoardResponse> {
            override fun onFailure(call: Call<GetDetailedBoardResponse>, t: Throwable) {
                Log.e("detailed_board fail", t.toString())
            }

            override fun onResponse(call: Call<GetDetailedBoardResponse>, response: Response<GetDetailedBoardResponse>) {
                if (response.isSuccessful) {
                    Log.v("ggg", "board list success")
                    imageUrlList.clear()
                    //보드연결
                    temp = response.body()!!.data

                    if(temp.category == "QUESTION"){
                        categoryListText[0].visibility = View.VISIBLE
                    }
                    else if(temp.category == "INSPIRATION"){
                        categoryListText[1].visibility = View.VISIBLE
                    }
                    else if(temp.category == "COWORKING"){
                        categoryListText[2].visibility = View.VISIBLE
                    }
                    //bindBoardData(temp)
                    Log.v("Asdf", "보드 값 = " + temp.toString())
                    et_up_board_title.setText(temp.title)
                    var keywordString : String = ""

                    for (i in 0..temp.keywords.size - 1) {
                        if (i == 0) {
                            keywordString = "#" + response.body()!!.data.keywords[i]
                        } else {
                            keywordString += "    #" + response.body()!!.data.keywords[i]
                        }
                    }
                    et_up_board_tags.setText(keywordString)
                    et_up_board_modify.setText(temp.content)
                    //rl_btn_up_board_category_selected.visibility = View.GONE
                    rl_btn_up_board_category_select.visibility = View.GONE
                    getImageUrlSize = temp.images.size
                    boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 0, getImageUrlSize)

                    // 서버로부터 받아온 이미지가 존재할 경우
                    if(temp.images.size>0)
                    {
                        getServerImageUrl = true
                        getImageUrlSize = temp.images.size - boardImageAdapter.urlRemovedCount

                    }
                    for(i in 0 .. temp.images.size-1){
                        imageUrlList.add(ImageType(temp.images[i],null))
                    }
                    boardImageAdapter = BoardImageAdapter(imageUrlList, requestManager,0, 0, getImageUrlSize)
                    //startUri = temp.images.size
                    upboard_pick_recyclerview.visibility = View.VISIBLE
                    upboard_pick_recyclerview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                    upboard_pick_recyclerview.adapter = boardImageAdapter
                }
            }
        })
    }


    private fun categorySelectOnClickListener() {

        /*각각의 카테고리 선택*/
        val categoryBtnList: Array<RelativeLayout> = arrayOf(
                rl_btn_up_board_category_question,
                rl_btn_up_board_category_inspriation,
                rl_btn_up_board_category_collaboration
        )


        //카테고리 선택시, 선택 창 닫힘 + 헤당 카테고리 글자 띄우기
        for (i in categoryBtnList.indices) {
            categoryBtnList[i].setOnClickListener {
                rl_btn_up_board_category_selected.visibility = View.GONE
                categoryListText[i].visibility = View.VISIBLE

                if(categoryListText[i].text.toString() == "질문")
                {
                    selectedCategory = "QUESTION"
                }
                else if(categoryListText[i].text.toString() == "영감")
                {
                    selectedCategory = "INSPIRATION"
                }
                else if(categoryListText[i].text.toString() == "협업")
                {
                    selectedCategory = "COWORKING"
                }

                ll_up_board_category_list.visibility = View.GONE
            }
        }

        //카테고리 선택 완료 후, 재 선택
        for (i in categoryListText.indices) {
            categoryListText[i].setOnClickListener {
                categoryListText[i].visibility = View.GONE
                rl_btn_up_board_category_selected.visibility = View.VISIBLE
                selectedCategory = categoryListText[i].text.toString()
                ll_up_board_category_list.visibility = View.VISIBLE
            }
        }

        /*카테고리 선택 창 열고 닫는 리스너*/
        //열기
        rl_btn_up_board_category_select.setOnClickListener {

            //more버튼 대신 less버튼으로
            rl_btn_up_board_category_select.visibility = View.GONE
            rl_btn_up_board_category_selected.visibility = View.VISIBLE
            //카테고리 리스트 선택 창
            ll_up_board_category_list.visibility = View.VISIBLE

        }
        //닫기
        rl_btn_up_board_category_selected.setOnClickListener {

            rl_btn_up_board_category_selected.visibility = View.GONE
            rl_btn_up_board_category_select.visibility = View.VISIBLE
            //카테고리 리스트 선택 창
            ll_up_board_category_list.visibility = View.GONE
        }
    }

    fun postBoard() {

        var token: String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        var networkService = ApplicationController.instance.networkService
        val title = RequestBody.create(MediaType.parse("text.plain"), et_up_board_title.text.toString())
        val content = RequestBody.create(MediaType.parse("text.plain"), et_up_board_modify.text.toString())
        val category = RequestBody.create(MediaType.parse("text.plain"), selectedCategory)

        val postBoardResponse = networkService.postBoard(token, title, content, category, imagesList, keywords)

        Log.v("TAG", "프로젝트 생성 전송 : 토큰 = " + token + ", 제목 = " + et_up_board_title.text.toString() + ", 내용 = " + et_up_board_modify.text.toString()
                + ", 카테고리 = " + selectedCategory + ", 키워드1 = " + keywords.get(0))

        postBoardResponse.enqueue(object : retrofit2.Callback<PostResponse> {

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if (response.isSuccessful) {
                    Log.v("TAG", "보드 값 전달 성공")
                    Log.v("TAG", "보드 status = " + response.body()!!.status)
                    Log.v("TAG", "보드 message = " + response.body()!!.message)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateBoard() {

        var token: String = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        var networkService = ApplicationController.instance.networkService
        val title = RequestBody.create(MediaType.parse("text.plain"), et_up_board_title.text.toString())
        val content = RequestBody.create(MediaType.parse("text.plain"), et_up_board_modify.text.toString())
        val category = RequestBody.create(MediaType.parse("text.plain"), selectedCategory)

        //val postBoardResponse = networkService.postBoard(token, title, content, category, imagesList, keywords)

        var postImages : ArrayList<MultipartBody.Part?> = ArrayList()
        postImages = imagesList

        Log.v("TAG", "프로젝트 생성 전송 : 토큰 = " + token)
        Log.v("TAG","제목 = " + et_up_board_title.text.toString())
        Log.v("TAG", "내용 = " + et_up_board_modify.text.toString())
        Log.v("TAG", "카테고리 = " + selectedCategory)
        for(i in 0 .. prevKeywords.size-1){
            Log.v("TAG", "이전 키워드 [" + i + "]번째 키워드 = " + prevKeywords.get(i))
        }
        for(i in 0 .. postKeywords.size-1){
            Log.v("TAG", "추가된 키워드 [" + i + "]번째 키워드 = " + postKeywords.get(i))
        }

        for(i in 0 .. prevImagesUrl.size-1){
            Log.v("TAG", "사라진 이미지 주소 [" + i + "]번째 url = " + prevImagesUrl.get(i))
        }
        for(i in 0 .. postImages.size-1){
            Log.v("TAG", "새로 추가된 이미지 [" + i + "]번째 파일 = " + postImages.get(i).toString())
        }

        /*
        postBoardResponse.enqueue(object : retrofit2.Callback<PostResponse> {

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if (response.isSuccessful) {
                    Log.v("TAG", "보드 값 전달 성공")
                    Log.v("TAG", "보드 status = " + response.body()!!.status)
                    Log.v("TAG", "보드 message = " + response.body()!!.message)
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.v("TAG", "보드 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
        */
    }

    companion object {
        lateinit var upBoardActivity: UpBoardActivity
    }

}
