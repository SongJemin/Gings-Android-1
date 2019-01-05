package com.computer.inu.myworkinggings.Hyunjin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.computer.inu.myworkinggings.Hyunjin.Activity.LoungeReservationActivity
import com.computer.inu.myworkinggings.Hyunjin.Data.LoungeEventData
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.startActivity

class LoungeEventDataRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<LoungeEventData>) : RecyclerView.Adapter<LoungeEventDataRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_lounge_event, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
//뷰 바인딩!!
        holder.title.text = dataList[position].title
        holder.date.text = dataList[position].date
        holder.time.text = dataList[position].time
        holder.place.text = dataList[position].place
        holder.all.setOnClickListener {
            ctx.startActivity<LoungeReservationActivity>()
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_title) as TextView
        val date : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_date)
        val time : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_time) as TextView
        val place : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_place)
        val all : RelativeLayout = itemView.findViewById(R.id.iv_rv_item_lounge_event_image) as RelativeLayout
    }
}