package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Hyunjin.Adapter.MessageDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageData
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_message.*


class MessageFragment : Fragment(){
    lateinit var MessageDataRecyclerViewAdapter: MessageDataRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }
    private fun setRecyclerView() {
//임시 데이터
        var dataList: ArrayList<MessageData> = ArrayList()
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        dataList.add(MessageData("깅스", "점심은 맘스터치 어때용", "2018/12/26 03:00"))
        MessageDataRecyclerViewAdapter = MessageDataRecyclerViewAdapter(activity!!, dataList)
        rv_item_message_list.adapter = MessageDataRecyclerViewAdapter
        rv_item_message_list.layoutManager = LinearLayoutManager(activity)
    }

}