package com.computer.inu.myworkinggings.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.data.ReBoardData
import java.util.ArrayList

class DetailBoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ReBoardData>)
    : RecyclerView.Adapter<DetailBoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_detailboard, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.time.text = dataList[position].time
        holder.contents_text.text = dataList[position].contents_text
        holder.name.text = dataList[position].name
    }


    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val time : TextView = itemView.findViewById(R.id.tv_item_detailboard_time) as TextView
        //val contents_img : TextView = itemView.findViewById(R.id.)
        //val contents_more : TextView = itemView.findViewById(R.id.tv_item_detailboard_contents_more) as TextView //boolean
        //val profile_img : TextView
        val name : TextView = itemView.findViewById(R.id.tv_item_detailboard_profile_name) as TextView
        val contents_text : TextView = itemView.findViewById(R.id.tv_item_detailboard_contents) as TextView
        //공유하기 val
        //더보기 val
    }
}