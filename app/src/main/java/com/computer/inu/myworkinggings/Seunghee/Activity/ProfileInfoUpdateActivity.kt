package com.computer.inu.myworkinggings.Seunghee.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Data.UserPageData
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_profile_info_update.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoUpdateActivity : AppCompatActivity() {

    lateinit var profileInformData : UserPageData
    lateinit var regionSpinner : Spinner
    lateinit var collabSpinner : Spinner
    lateinit var statusSpinner : Spinner

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info_update)
        getProfileInform()

        bt_profile_info_update_complete.setOnClickListener {
            putProfileInfo()
        }

    }

    fun getProfileInform(){
        var getMypageResponse: Call<GetMypageResponse> = networkService.getMypageResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg")
        getMypageResponse.enqueue(object : Callback<GetMypageResponse> {
            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                if (response!!.isSuccessful) {
                    profileInformData = response!!.body()!!.data
                    regionSpinner = findViewById(R.id.sp_profile_info_update_city) as Spinner
                    collabSpinner = findViewById(R.id.sp_profile_info_update_co_available) as Spinner
                    statusSpinner = findViewById(R.id.sp_profile_info_update_co_state) as Spinner

                    var findPosition : Int = 0
                    findPosition = findSpinnerData(regionSpinner, profileInformData.region)
                    regionSpinner.setSelection(findPosition)
                    et_profile_info_update_role.hint = profileInformData.job
                    et_profile_info_update_part.hint = profileInformData.company
                    et_profile_info_update_co_part.hint = profileInformData.field
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
                    et_profile_info_update_keyword.hint = keywordString
                        /*
                        if(profileInformData.region == "서울특별시")
                            regionSpinner.setSelection(0)
                        else if(profileInformData.region == "부산광역시")
                            regionSpinner.setSelection(1)
                        else if(profileInformData.region == "인천광역시")
                            regionSpinner.setSelection(2)
                        else if(profileInformData.region == "대구광역시")
                            regionSpinner.setSelection(3)
                        else if(profileInformData.region == "광주광역시")
                            regionSpinner.setSelection(4)
                        else if(profileInformData.region == "대전광역시")
                            regionSpinner.setSelection(5)
                        else if(profileInformData.region == "울산광역시")
                            regionSpinner.setSelection(6)
                        else if(profileInformData.region == "세종특별자치시")
                            regionSpinner.setSelection(7)
                        else if(profileInformData.region == "경기도")
                            regionSpinner.setSelection(8)
                        else if(profileInformData.region == "강원도")
                            regionSpinner.setSelection(9)
                        else if(profileInformData.region == "충청북도")
                            regionSpinner.setSelection(10)
                        else if(profileInformData.region == "충청남도")
                            regionSpinner.setSelection(11)
                        else if(profileInformData.region == "경상북도")
                            regionSpinner.setSelection(12)
                        else if(profileInformData.region == "경상남도")
                            regionSpinner.setSelection(13)
                        else if(profileInformData.region == "전락북도")
                            regionSpinner.setSelection(14)
                        else if(profileInformData.region == "전라남도")
                            regionSpinner.setSelection(15)
                        else if(profileInformData.region == "제주도")
                            regionSpinner.setSelection(16)
                            */
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

    fun putProfileInfo()
    {
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
                    finish()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable?) {
            }
        })
    }

}
