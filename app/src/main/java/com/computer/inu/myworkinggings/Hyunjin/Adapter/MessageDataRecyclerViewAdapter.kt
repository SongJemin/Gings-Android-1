package com.computer.inu.myworkinggings.Hyunjin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageData
import com.computer.inu.myworkinggings.Hyunjin.Activity.MessageSend1Activity
import com.computer.inu.myworkinggings.Jemin.Activity.ChatActivity
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.startActivity


class MessageDataRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<MessageData>) : RecyclerView.Adapter<MessageDataRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_message, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
//뷰 바인딩!!
        holder.title.text = dataList[position].title
        holder.content.text = dataList[position].content
        holder.time.text = dataList[position].time
        holder.all.setOnClickListener {
            ctx.startActivity<ChatActivity>()
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.tv_rv_item_message_title) as TextView
        val content : TextView = itemView.findViewById(R.id.tv_rv_item_message_content) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_rv_item_message_time) as TextView
        val all : RelativeLayout = itemView.findViewById(R.id.rv_item_message) as RelativeLayout
    }
}