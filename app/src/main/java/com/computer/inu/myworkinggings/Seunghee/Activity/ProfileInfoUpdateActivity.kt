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
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_profile_info_update.*
import kotlinx.android.synthetic.main.activity_up_board.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.ctx
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
    var regionResult : String = ""
    var collabResult : String = ""
    var statusResult : String = ""
    var keywords = ArrayList<String>()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info_update)
        regionSpinner = findViewById(R.id.sp_profile_info_update_city) as Spinner
        collabSpinner = findViewById(R.id.sp_profile_info_update_co_available) as Spinner
        statusSpinner = findViewById(R.id.sp_profile_info_update_co_state) as Spinner

        collabSpinner.setSelection(0)
        collabResult = "true"

        statusSpinner.setSelection(0)
        statusResult = "OPENED"

        regionSpinner.setSelection(0)
        regionResult = "SEOUL"
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
                // 지역 스피너
                if(regionSpinner.getSelectedItem().toString() == "서울특별시"){
                    regionResult = "SEOUL"
                }
                else if(regionSpinner.getSelectedItem().toString() == "세종특별자치시"){
                    regionResult = "SEJONG"
                }
                else if(regionSpinner.getSelectedItem().toString() == "부산광역시"){
                    regionResult = "BUSAN"
                }
                else if(regionSpinner.getSelectedItem().toString() == "대구광역시"){
                    regionResult = "DAEGU"
                }
                else if(regionSpinner.getSelectedItem().toString() == "대전광역시"){
                    regionResult = "DAEJEON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "인천광역시"){
                    regionResult = "INCHEON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "울산광역시"){
                    regionResult = "ULSAN"
                }
                else if(regionSpinner.getSelectedItem().toString() == "광주광역시"){
                    regionResult = "GWANGJU"
                }
                else if(regionSpinner.getSelectedItem().toString() == "강원도"){
                    regionResult = "GANGWON"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경기도"){
                    regionResult = "GYUNGGI"
                }
                else if(regionSpinner.getSelectedItem().toString() == "충청남도"){
                    regionResult = "CHUNG_NAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "충청북도"){
                    regionResult = "CHUNG_BUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "전라북도"){
                    regionResult = "JEON_BUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "전라남도"){
                    regionResult = "JEON_NAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경상남도"){
                    regionResult = "GYEONG_NAM"
                }
                else if(regionSpinner.getSelectedItem().toString() == "경상북도"){
                    regionResult = "GYEONG_BUK"
                }
                else if(regionSpinner.getSelectedItem().toString() == "제주도"){
                    regionResult = "JEJU"
                }
                else{
                    regionResult = "NONE"
                }

                // 상태 스피너
                if(statusSpinner.getSelectedItem().toString() == "열려있음"){
                    statusResult = "OPENED"
                }
                else if(statusSpinner.getSelectedItem().toString() == "준비중"){
                    statusResult = "PREPARING"
                }
                else if(statusSpinner.getSelectedItem().toString() == "본업"){
                    statusResult = "MAIN_JOB"
                }
                else if(statusSpinner.getSelectedItem().toString() == "프리랜서"){
                    statusResult = "FREELANCER"
                }
                else if(statusSpinner.getSelectedItem().toString() == "구인중"){
                    statusResult = "RECRUITING"
                }
                else if(statusSpinner.getSelectedItem().toString() == "구직중"){
                    statusResult = "LOOKING_JOB"
                }
                else if(statusSpinner.getSelectedItem().toString() == "투자후원유중"){
                    statusResult = "LOOKING_INVESTMENT"
                }

                // 협업 스피너
                if(collabSpinner.getSelectedItem().toString() == "가능"){
                    collabResult = "true"
                }
                else{
                    collabResult = "false"
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
        var getMypageResponse: Call<GetMypageResponse> = networkService.getMypageResponse("application/json",SharedPreferenceController.getAuthorization(ctx))
        getMypageResponse.enqueue(object : Callback<GetMypageResponse> {
            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                if (response!!.isSuccessful) {
                    if(response!!.body()!!.data.region != "NONE"){
                        Log.v("프로필 등록 ", "수정")
                        profileInformData = response!!.body()!!.data

                        var findPosition : Int = 0
                        var getRegionValue : String = ""
                        var getStatusValue : String = ""
                        var getCollabValue : String = ""

                        // 지역 스피너
                        if(profileInformData.region!! == "SEOUL"){
                            getRegionValue = "서울특별시"
                        }
                        else if(profileInformData.region!! == "SEJONG"){
                            getRegionValue = "세종특별자치시"
                        }
                        else if(profileInformData.region!! == "BUSAN"){
                            getRegionValue = "부산광역시"
                        }
                        else if(profileInformData.region!! == "DAEGU"){
                            getRegionValue = "대구광역시"
                        }
                        else if(profileInformData.region!! == "DAEJEON"){
                            getRegionValue = "대전광역시"
                        }
                        else if(profileInformData.region!! == "INCHEON"){
                            getRegionValue = "인천광역시"
                        }
                        else if(profileInformData.region!! == "ULSAN"){
                            getRegionValue = "울산광역시"
                        }
                        else if(profileInformData.region!! == "GWANGJU"){
                            getRegionValue = "광주광역시"
                        }
                        else if(profileInformData.region!! == "GANGWON"){
                            getRegionValue = "강원도"
                        }
                        else if(profileInformData.region!! == "GYUNGGI"){
                            getRegionValue = "경기도"
                        }
                        else if(profileInformData.region!! == "CHUNG_NAM"){
                            getRegionValue = "충청남도"
                        }
                        else if(profileInformData.region!! == "CHUNG_BUK"){
                            getRegionValue = "충청북도"
                        }
                        else if(profileInformData.region!! == "JEON_BUK"){
                            getRegionValue = "전라북도"
                        }
                        else if(profileInformData.region!! == "JEON_NAM"){
                            getRegionValue = "전라남도"
                        }
                        else if(profileInformData.region!! == "GYEONG_NAM"){
                            getRegionValue = "경상남도"
                        }
                        else if(profileInformData.region!! == "GYEONG_BUK"){
                            getRegionValue = "경상북도"
                        }
                        else if(profileInformData.region!! == "JEJU"){
                            getRegionValue = "제주도"
                        }
                        else{
                            getRegionValue = "NONE"
                        }


                        findPosition = findSpinnerData(regionSpinner, getRegionValue)
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


                        // 상태 스피너
                        if(profileInformData.status!! == "OPENED"){
                            getStatusValue = "열려있음"
                        }
                        else if(profileInformData.status!! == "PREPARING"){
                            getStatusValue = "준비중"
                        }
                        else if(profileInformData.status!! == "MAIN_JOB"){
                            getStatusValue = "본업"
                        }
                        else if(profileInformData.status!! == "FREELANCER"){
                            getStatusValue = "프리랜서"
                        }
                        else if(profileInformData.status!! == "RECRUITING"){
                            getStatusValue = "구인중"
                        }
                        else if(profileInformData.status!! == "LOOKING_JOB"){
                            getStatusValue = "구직중"
                        }
                        else if(profileInformData.status!! == "LOOKING_INVESTMENT"){
                            getStatusValue = "투자후원유중"
                        }

                        findPosition = findSpinnerData(statusSpinner, getStatusValue)
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
                    else{
                        Log.v("프로필 등록 ", "처음 조회")
                    }

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

        Log.v("프로필", "장소 = " + regionResult )
        Log.v("프로필", "역할 = " + et_profile_info_update_role.text.toString() )
        Log.v("프로필", "소속 = " + et_profile_info_update_part.text.toString() )
        Log.v("프로필", "분야 = " + et_profile_info_update_co_part.text.toString() )
        Log.v("프로필", "상태 = " + statusResult )
        Log.v("프로필", "협업여부 = " + collabResult )

        var jsonObject = JSONObject()
        jsonObject.put("region", regionResult)
        jsonObject.put("job", et_profile_info_update_role.text.toString())
        jsonObject.put("company", et_profile_info_update_part.text.toString())
        jsonObject.put("field", et_profile_info_update_co_part.text.toString())
        jsonObject.put("status", statusResult)
        jsonObject.put("coworkingEnabled", collabResult)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        var putProfileInfoResponse = networkService.putProfileInfo(SharedPreferenceController.getAuthorization(this), gsonObject)
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
        Log.v("프로필", "키워드 = " + keywords.toString() )
        var postKeywords = PostKeywords(keywords)
        var putProfileInfoResponse = networkService.postKeywordList(SharedPreferenceController.getAuthorization(this), postKeywords)
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
