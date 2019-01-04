package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.GET.ReplyData
import java.util.ArrayList

class DetailBoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ReplyData?>)
    : RecyclerView.Adapter<DetailBoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_detailboard, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder.name.text = dataList[position].name

        holder.name.text = dataList[position]!!.writer
        holder.time.text = dataList[position]!!.writeTime
        holder.contents_text.text = dataList[position]!!.content

        }


    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val time : TextView = itemView.findViewById(R.id.tv_item_detailboard_time) as TextView
        val name : TextView = itemView.findViewById(R.id.tv_item_detailboard_profile_name) as TextView
        val contents_text : TextView = itemView.findViewById(R.id.tv_item_detailboard_contents) as TextView

    }
}