package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Hyunjin.Data.AlarmData
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.adapter.AlarmDataRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_notice.*

class NoticeFragment : Fragment(){

    lateinit var AlarmDataRecyclerViewAdapter: AlarmDataRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_notice, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }
    private fun setRecyclerView() {
//임시 데이터
        var dataList: ArrayList<AlarmData> = ArrayList()
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        dataList.add(AlarmData("[질문보드]ㅇㅇㅇ님의 답변이 달렸어요.", "잘 해보세요~!! 화이팅", "12/26 12:30"))
        AlarmDataRecyclerViewAdapter = AlarmDataRecyclerViewAdapter(activity!!, dataList)
        rv_item_alarm_list.adapter = AlarmDataRecyclerViewAdapter
        rv_item_alarm_list.layoutManager = LinearLayoutManager(activity)
    }

}