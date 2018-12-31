package com.computer.inu.myworkinggings.Hyunjin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.Hyunjin.Data.MessageSendData
import com.computer.inu.myworkinggings.R

class MessageSendDataRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<MessageSendData>) : RecyclerView.Adapter<MessageSendDataRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageSendDataRecyclerViewAdapter.Holder {
//뷰 인플레이트!!
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_message_send1, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: MessageSendDataRecyclerViewAdapter.Holder, position: Int) {
//뷰 바인딩!!
        holder.Receiver.text = dataList[position].Receiver
        holder.Receiver_time.text = dataList[position].Receiver_time
        holder.Sender.text = dataList[position].Sender
        holder.Sender_time.text = dataList[position].Sender_time
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val Receiver : TextView = itemView.findViewById(R.id.tv_rv_item_message_send1_receiver) as TextView
        val Receiver_time : TextView = itemView.findViewById(R.id.tv_rv_item_message_send1_receiver_time) as TextView
        val Sender : TextView = itemView.findViewById(R.id.tv_rv_item_message_send1_content) as TextView
        val Sender_time : TextView = itemView.findViewById(R.id.tv_rv_item_message_send1_time) as TextView
    }
}