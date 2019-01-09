package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Hyunjin.Adapter.LoungeDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Get.ClubData
import com.computer.inu.myworkinggings.Hyunjin.Get.GetSearchClub
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import kotlinx.android.synthetic.main.fragment_lounge.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoungeFragment : Fragment(){

    lateinit var LoungeDataRecyclerViewAdapter: LoungeDataRecyclerViewAdapter

    lateinit var networkService : NetworkService
    var dataList: ArrayList<ClubData> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lounge,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        networkService = ApplicationController.instance.networkService
        getSearchClub()
    }

    fun getSearchClub(){
        var getSearchClubDataResponse = networkService.getSearchClub("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds") // 네트워크 서비스의 getContent 함수를 받아옴
        getSearchClubDataResponse.enqueue(object : Callback<GetSearchClub> {
            override fun onResponse(call: Call<GetSearchClub>?, response: Response<GetSearchClub>?) {
                Log.v("TAG", "GET 통신 성공")
                if (response!!.isSuccessful) {
                    dataList = response.body()!!.data
                    //response.body()!!.data[].clubId
                    Log.v("TAG", "클럽 조회 통신 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)


                    //Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()

                    LoungeDataRecyclerViewAdapter = LoungeDataRecyclerViewAdapter(activity!!, dataList)
                    rv_lounge_frag_lounge_list.adapter = LoungeDataRecyclerViewAdapter
                    rv_lounge_frag_lounge_list.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
                }
                else{
                    Toast.makeText(context,"에러 사유 = " + response.errorBody(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GetSearchClub>?, t: Throwable?) {
                Toast.makeText(context,"통신 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

}