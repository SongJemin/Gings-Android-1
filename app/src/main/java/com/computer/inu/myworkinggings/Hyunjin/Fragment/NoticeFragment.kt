package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Hyunjin.Data.AlarmData
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DirectoryBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryData
import com.computer.inu.myworkinggings.Moohyeon.Data.MyAlarmData
import com.computer.inu.myworkinggings.Moohyeon.get.GetAlarmResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetDirectoryListResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import com.computer.inu.myworkinggings.adapter.AlarmDataRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_directory.*
import kotlinx.android.synthetic.main.fragment_directory.view.*
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_notice.view.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeFragment : Fragment(){
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var AlarmDataRecyclerViewAdapter: AlarmDataRecyclerViewAdapter
   var alarmData = ArrayList<AlarmData>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_notice, container, false)

        AlarmDataRecyclerViewAdapter =AlarmDataRecyclerViewAdapter(context!!, alarmData)
        view.rv_item_alarm_list.layoutManager = LinearLayoutManager(view.context)
        view.rv_item_alarm_list.adapter =  AlarmDataRecyclerViewAdapter
        view.rv_item_alarm_list.setNestedScrollingEnabled(false)
        getAlarmResponse()

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    fun getAlarmResponse() {
        var getalarmResponse: Call<GetAlarmResponse> = networkService.getAlarmResponse("application/json",  SharedPreferenceController.getAuthorization(context!!))
       getalarmResponse.enqueue(object : Callback<GetAlarmResponse> {
            override fun onResponse(call: Call<GetAlarmResponse>?, response: Response<GetAlarmResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {

                    var temp: ArrayList<AlarmData> = response.body()!!.data
                    if (temp != null) {
                        var position =  AlarmDataRecyclerViewAdapter.itemCount
                        AlarmDataRecyclerViewAdapter.dataList.addAll(temp)
                        AlarmDataRecyclerViewAdapter.notifyItemInserted(position)

                    }
                } else {
                    Log.v("TAG", "디렉토리 검색 실패")
                    toast("통신 실패")
                }
            }

            override fun onFailure(call: Call<GetAlarmResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }
}