package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Hyunjin.Adapter.MessageSendDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageSendData
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_messagesend1.*

class MessageSend1Fragment : Fragment(){
    lateinit var MessageSendDataRecyclerViewAdapter: MessageSendDataRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messagesend1,container,false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

    }

    private fun setRecyclerView() {
//임시 데이터
        var dataList: ArrayList<MessageSendData> = ArrayList()
        dataList.add(MessageSendData("돼지감자차 자연의 건강함이 가득 담겨있는 담백하고 풍부한 맛의 깊이를 느낄수 있는 차입니다. 차 우리는 시간은 3분입니다.", "12월 4일, 18:33", "쌍계명가 돼지감자차 청정한 자연의 차, 품격 있는 명인의 차, 문화가 담긴 차! Jerusalem Artichoke Tea!!","12월 4일, 18:03"))
        dataList.add(MessageSendData("돼지감자차 자연의 건강함이 가득 담겨있는 담백하고 풍부한 맛의 깊이를 느낄수 있는 차입니다. 차 우리는 시간은 3분입니다.", "12월 4일, 18:33", "쌍계명가 돼지감자차 청정한 자연의 차, 품격 있는 명인의 차, 문화가 담긴 차! Jerusalem Artichoke Tea!!","12월 4일, 18:03"))
        dataList.add(MessageSendData("돼지감자차 자연의 건강함이 가득 담겨있는 담백하고 풍부한 맛의 깊이를 느낄수 있는 차입니다. 차 우리는 시간은 3분입니다.", "12월 4일, 18:33", "쌍계명가 돼지감자차 청정한 자연의 차, 품격 있는 명인의 차, 문화가 담긴 차! Jerusalem Artichoke Tea!!","12월 4일, 18:03"))
        MessageSendDataRecyclerViewAdapter = MessageSendDataRecyclerViewAdapter(activity!!, dataList)
        rv_item_messagesend_list.adapter = MessageSendDataRecyclerViewAdapter
        rv_item_messagesend_list.layoutManager = LinearLayoutManager(activity)
    }

}