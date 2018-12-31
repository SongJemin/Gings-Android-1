package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.Moohyeon.Data.SearchingUserData
import com.computer.inu.myworkinggings.R
import java.util.ArrayList

class SearchingUserRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<SearchingUserData>)
    : RecyclerView.Adapter<SearchingUserRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_directory_searching_result, parent, false) // rv_item 수정 해야함
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].name
        holder.team_name.text=dataList[position].team_name
        holder.role.text=dataList[position].role
        holder.part.text=dataList[position].part
        holder.ablity.text=dataList[position].ability
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tv_item_directory_searching_profile_name) as TextView
        val team_name : TextView = itemView.findViewById(R.id.tv_item_directory_searching_team_name) as TextView
        val role : TextView = itemView.findViewById(R.id.tv_item_directory_searching_role) as TextView
        val part : TextView = itemView.findViewById(R.id.tv_item_directory_searching_part) as TextView
        val ablity: TextView = itemView.findViewById(R.id.tv_item_directory_searching_ablity) as TextView
    }
}