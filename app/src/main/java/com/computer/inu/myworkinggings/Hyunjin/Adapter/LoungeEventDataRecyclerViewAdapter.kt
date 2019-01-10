package com.computer.inu.myworkinggings.Hyunjin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Hyunjin.Activity.LoungeReservationActivity
import com.computer.inu.myworkinggings.Hyunjin.Data.LoungeEventData
import com.computer.inu.myworkinggings.Hyunjin.Get.EventDetailData
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.R.id.btn_lounge_event_detail_join
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity

class LoungeEventDataRecyclerViewAdapter(val ctx : Context,var clubId: Int, val dataList : ArrayList<EventDetailData>) : RecyclerView.Adapter<LoungeEventDataRecyclerViewAdapter.Holder>(){

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
            ctx.startActivity<LoungeReservationActivity>("eventId" to dataList[position].eventId,"clubId" to  clubId)
        }
        Glide.with(ctx).load(dataList[position].eventImg).centerCrop().into(holder.eventImage)
     //   Glide.with(ctx).load(dataList[position].eventStatus).centerCrop().into(holder.eventStatus)


    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val eventImage : ImageView = itemView.findViewById(R.id.rl_iv_lounge_event_eventImg) as ImageView
        val title : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_title) as TextView
        val date : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_date) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_time) as TextView
        val place : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_event_place) as TextView
        val all : RelativeLayout = itemView.findViewById(R.id.iv_rv_item_lounge_event_image) as RelativeLayout
    }
}