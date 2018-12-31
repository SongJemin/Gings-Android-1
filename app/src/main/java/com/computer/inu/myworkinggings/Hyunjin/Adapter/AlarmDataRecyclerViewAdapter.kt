
package com.computer.inu.myworkinggings.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.Hyunjin.Data.AlarmData
import com.computer.inu.myworkinggings.R


class AlarmDataRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<AlarmData>) : RecyclerView.Adapter<AlarmDataRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_alarm, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
//뷰 바인딩!!
        holder.title.text = dataList[position].title
        holder.content.text = dataList[position].content
        holder.time.text = dataList[position].time
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.tv_rv_item_alarm_title) as TextView
        val content : TextView = itemView.findViewById(R.id.tv_rv_item_alarm_content) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_rv_item_alarm_time) as TextView
    }
}