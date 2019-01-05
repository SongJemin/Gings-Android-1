package com.computer.inu.myworkinggings.Seunghee.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import com.computer.inu.myworkinggings.Jemin.Activity.MypageUpdateActivity
import com.computer.inu.myworkinggings.Jemin.Activity.PasswdModifyActivity
import com.computer.inu.myworkinggings.Jemin.Activity.UnsubscribeActivity
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_profile_setting_menu.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileSettingMenuActivity : AppCompatActivity() {

    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    private var profileIImage : MultipartBody.Part? = null
    var profileImgUrl : String = ""

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting_menu)

        /*프로필 정보*/
        //프로필 사진 수정
        rl_btn_profile_setting_menu_image_modify.setOnClickListener {
            changeImage()
        }

        //프로필 정보 수정
        rl_btn_profile_setting_menu_profile_info_update.setOnClickListener{
            var intent = Intent(applicationContext, ProfileInfoUpdateActivity::class.java)
            startActivityForResult(intent,27)
        }
        //자기소개 수정
        rl_btn_profile_setting_menu_mypage_update.setOnClickListener {
            startActivity<MypageUpdateActivity>()
        }


        /*보안*/
        //비밀번호 변경
        rl_btn_profile_setting_menu_password_modify.setOnClickListener {
            startActivity<PasswdModifyActivity>()
        }
        //로그아웃

        //탈퇴하기
        rl_btn_profile_setting_menu_unsubscrible.setOnClickListener{
            startActivity<UnsubscribeActivity>()
        }


        /**/
        //종료버튼
        iv_btn_profile_setting_close.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in)

        }
    }

    fun changeImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)

    }

    // 갤러리로부터 이미지 갖고올 때 사용하는 오버라이딩 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 27){

        }
        else if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    this.data = data!!.data
                    Log.v("asdf","이미지0 = " + data!!.data)

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                        Log.v("asdf","이미지0.5 = " + input)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    Log.v("asdf","이미지1 = " + photoBody)
                    Log.v("asdf","이미지2 = " + photoBody.toString())
                    val imgFile = File(getRealPathFromURI(applicationContext,this.data).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다
                    Log.v("asdf","이미지3 = " + imgFile)
                    Log.v("asdf","이미지4 " + imgFile.toString())
                    Log.v("asdf","이미지5 = " + imgFile.name)
                    profileIImage = MultipartBody.Part.createFormData("imgFile", imgFile.name, photoBody)
                    putProfileImg()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    // 이미지 파일을 확장자까지 표시해주는 메소드
    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun putProfileImg() {

        val image = RequestBody.create(MediaType.parse("text.plain"), profileImgUrl)

        val putProfileImgUrlResponse = networkService.putMyProfileImg("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", image, profileIImage)

        putProfileImgUrlResponse.enqueue(object : retrofit2.Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "프로필사진 값 전달 성공")
                }
                else{
                    Log.v("TAG", "프로필사진 값 전달 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"프로필사진 서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
