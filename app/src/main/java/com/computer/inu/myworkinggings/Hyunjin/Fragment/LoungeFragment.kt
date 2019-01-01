package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Hyunjin.Adapter.LoungeDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Data.LoungeData
import kotlinx.android.synthetic.main.fragment_lounge.*


class LoungeFragment : Fragment(){

    lateinit var LoungeDataRecyclerViewAdapter: LoungeDataRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lounge,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()

    }
    private fun setRecyclerView() {
//임시 데이터
        var dataList: ArrayList<LoungeData> = ArrayList()
        dataList.add(LoungeData("Innovator Club"))
        dataList.add(LoungeData("Innovator Club"))

        LoungeDataRecyclerViewAdapter = LoungeDataRecyclerViewAdapter(activity!!, dataList)
        rv_lounge_frag_lounge_list.adapter = LoungeDataRecyclerViewAdapter
        rv_lounge_frag_lounge_list.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
    }
}