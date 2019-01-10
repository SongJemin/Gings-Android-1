package com.computer.inu.myworkinggings.Seunghee.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.POST.PostKeywords
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Data.UserPageData
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_profile_info_update.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoUpdateActivity : AppCompatActivity() {

    lateinit var profileInformData : UserPageData
    lateinit var regionSpinner : Spinner
    lateinit var collabSpinner : Spinner
    lateinit var statusSpinner : Spinner
    var regionValue : String = ""
    var keywords = ArrayList<String>()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info_update)
        regionSpinner = findViewById(R.id.sp_profile_info_update_city) as Spinner
        regionSpinner.setSelection(0)
        regionValue = "SEOUL"
        getProfileInform()

        bt_profile_info_update_complete.setOnClickListener {
            if(et_profile_info_update_role.text.toString().isEmpty()
            || et_profile_info_update_part.text.toString().isEmpty()
            || et_profile_info_update_co_part.text.toString().isEmpty()
                    || et_profile_info_update_keyword.text.toString().isEmpty()
            ){
                if(et_profile_info_update_role.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"역할을 입력하세요.",Toast.LENGTH_LONG).show()
                }
                else if(et_profile_info_update_part.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"소속을 입력하세요.",Toast.LENGTH_LONG).show()
                }
                else if(et_profile_info_update_co_part.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"활동 분야를 입력하세요.",Toast.LENGTH_LONG).show()
                }
                else if(et_profile_info_update_keyword.text.toString().isEmpty() || et_profile_info_update_role.text.toString().isEmpty()){
                    Toast.makeText(applicationContext,"키워드를 입력하세요.",Toast.LENGTH_LONG).show()
                }
            }
            else{
                if(regionSpinner.getSelectedItem().toString() == "서울"){
                    regionValue = "SEOUL"
                }
                else if(regionSpinner.getSelectedItem().toString() == "세종"){
                    regionValue = "SEJONG"
                }
                else if(regionSpinner.getSelectedItem().toString() == "부산"){
                    regionValue = "PUSAN"
                }
                else if(regionSpinner.getSelectedItem().toString() == "대구"){
                    regionValue = "DAEGU"
                }
                else if(regionSpinner.getSelectedItem().toString() == "대전"){
                    regionValue = "DAEJEON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "인천"){
                    regionValue = "INCHEON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "울산"){
                    regionValue = "ULSAN"
                }
                else if(regionSpinner.getSelectedItem().toString() == "광주"){
                    regionValue = "GWANGJU"
                }
                else if(regionSpinner.getSelectedItem().toString() == "강원"){
                    regionValue = "GANGWON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경기"){
                    regionValue = "GYEONGGI"
                }
                else if(regionSpinner.getSelectedItem().toString() == "충남"){
                    regionValue = "CHUNGNAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "충북"){
                    regionValue = "CHUNGBUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "전북"){
                    regionValue = "JEONBUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "전남"){
                    regionValue = "JEONNAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경남"){
                    regionValue = "GYEONGNAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경북"){
                    regionValue = "GYEONGBUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "제주"){
                    regionValue = "JEJU"
                }
                else{
                    regionValue = "NONE"
                }

                val keywordList = et_profile_info_update_keyword.text.toString().split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                for (keyword in keywordList) {
                    Log.v("Asdf", "키워드 자르기 = " +   keyword.replace("#", ""))
                    keywords.add(keyword.replace("#", ""))
                }

                putProfileInfo()
            }


        }

    }

    fun getProfileInform(){
        var getMypageResponse: Call<GetMypageResponse> = networkService.getMypageResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg")
        getMypageResponse.enqueue(object : Callback<GetMypageResponse> {
            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                if (response!!.isSuccessful) {
                    profileInformData = response!!.body()!!.data

                    collabSpinner = findViewById(R.id.sp_profile_info_update_co_available) as Spinner
                    statusSpinner = findViewById(R.id.sp_profile_info_update_co_state) as Spinner

                    var findPosition : Int = 0
                    findPosition = findSpinnerData(regionSpinner, profileInformData.region)
                    regionSpinner.setSelection(findPosition)
                    et_profile_info_update_role.setText(profileInformData.job)
                    et_profile_info_update_part.setText(profileInformData.company)
                    et_profile_info_update_co_part.setText(profileInformData.field)
                    if(profileInformData.coworkingEnabled == true){
                        findPosition = findSpinnerData(collabSpinner, "가능")
                        collabSpinner.setSelection(findPosition)
                    }
                    else{
                        findPosition = findSpinnerData(collabSpinner, "불가능")
                        collabSpinner.setSelection(findPosition)
                    }
                    findPosition = findSpinnerData(statusSpinner, profileInformData.status)
                    statusSpinner.setSelection(findPosition)

                    var keywordString : String = ""

                    for (i in 0..profileInformData.keywords.size - 1) {
                        Log.v("asdf","키워드 우에 = " + response.body()!!.data.keywords[i])
                        if (i == 0) {
                            keywordString = "#" + response.body()!!.data.keywords[i]
                        } else {
                            keywordString += "    #" + response.body()!!.data.keywords[i]
                        }
                    }
                    Log.v("asdf","키워드 값 = " + keywordString)
                    et_profile_info_update_keyword.setText(keywordString)
                }
            }

            override fun onFailure(call: Call<GetMypageResponse>?, t: Throwable?) {

                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

    fun findSpinnerData(spinner: Spinner, findData: String): Int {
        val adapter = spinner.adapter as ArrayAdapter<String>
        return adapter.getPosition(findData)
    }

    fun putProfileInfo(){
        var collabResult : String = ""
        if(collabSpinner.getSelectedItem().toString() == "가능"){
            collabResult = "true"
        }
        else{
            collabResult = "false"
        }
        var jsonObject = JSONObject()
        jsonObject.put("region", regionSpinner.getSelectedItem().toString())
        jsonObject.put("job", et_profile_info_update_role.text.toString())
        jsonObject.put("company", et_profile_info_update_part.text.toString())
        jsonObject.put("field", et_profile_info_update_co_part.text.toString())
        jsonObject.put("status", regionSpinner.getSelectedItem().toString())
        jsonObject.put("coworkingEnabled", collabResult)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        var putProfileInfoResponse = networkService.putProfileInfo("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", gsonObject)
        putProfileInfoResponse.enqueue(object : Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if(response.isSuccessful){
                    Log.v("asdf", "프로필 정보 등록 성공")
                    postKeywordList()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
            }
        })
    }

    fun postKeywordList(){

        var postKeywords = PostKeywords(keywords)
        var putProfileInfoResponse = networkService.postKeywordList("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", postKeywords)
        putProfileInfoResponse.enqueue(object : Callback<PostResponse>{

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if(response.isSuccessful){
                    Log.v("asdf", "키워드 리스트 전송 성공")
                    finish()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
            }
        })
    }

}
