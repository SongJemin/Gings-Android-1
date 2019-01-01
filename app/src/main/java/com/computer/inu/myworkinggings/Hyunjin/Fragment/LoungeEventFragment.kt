package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.computer.inu.myworkinggings.Hyunjin.Adapter.LoungeDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Adapter.LoungeEventDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.LoungeData
import com.computer.inu.myworkinggings.Hyunjin.Data.LoungeEventData
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragement_lounge_event.*
import kotlinx.android.synthetic.main.fragment_lounge.*

class LoungeEventFragment : Fragment(){

    lateinit var LoungeEventDataRecyclerViewAdapter: LoungeEventDataRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_lounge_event,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

    }
    private fun setRecyclerView() {
//임시 데이터
        var dataList: ArrayList<LoungeEventData> = ArrayList()
        dataList.add(LoungeEventData("혁신콘서트","2019/04/13(목)","12:00 - 17:00","서울청년창업허브"))
        dataList.add(LoungeEventData("혁신콘서트","2019/04/13(목)","12:00 - 17:00","서울청년창업허브"))

        LoungeEventDataRecyclerViewAdapter = LoungeEventDataRecyclerViewAdapter(activity!!, dataList)
        rv_lounge_frag_lounge_event_list.adapter = LoungeEventDataRecyclerViewAdapter
        rv_lounge_frag_lounge_event_list.layoutManager = LinearLayoutManager(activity)
    }


}
