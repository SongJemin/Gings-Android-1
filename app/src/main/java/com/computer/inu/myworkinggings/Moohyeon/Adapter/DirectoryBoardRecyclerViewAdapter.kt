package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryBoardData
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.toast
import java.util.ArrayList

class DirectoryBoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<DirectoryBoardData>)
    : RecyclerView.Adapter<DirectoryBoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_directory_user_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.time.text = dataList[position].time
        holder.contents_text.text = dataList[position].contents_text
        holder.name.text = dataList[position].name
    }


    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val time : TextView = itemView.findViewById(R.id.tv_item_directory_time) as TextView
        //val contents_img : TextView = itemView.findViewById(R.id.)
        //val contents_more : TextView = itemView.findViewById(R.id.tv_item_directory_contents_more) as TextView //boolean
        //val profile_img : TextView
        val name : TextView = itemView.findViewById(R.id.tv_item_directory_profile_name) as TextView
        val contents_text : TextView = itemView.findViewById(R.id.tv_item_directory_contents) as TextView
        //공유하기 val
        //더보기 val
    }
}