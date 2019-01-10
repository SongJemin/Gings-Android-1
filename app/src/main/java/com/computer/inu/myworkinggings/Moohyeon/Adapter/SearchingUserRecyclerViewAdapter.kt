package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectorySearchData
import com.computer.inu.myworkinggings.Moohyeon.Data.SearchingUserData
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.R.drawable.box_on
import java.util.ArrayList

class SearchingUserRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<DirectorySearchData>)
    : RecyclerView.Adapter<SearchingUserRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_directory_searching_result, parent, false) // rv_item 수정 해야함
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].name
        holder.team_name.text=dataList[position].company

       if(dataList[position].job==""||dataList[position].job==" "||dataList[position].job==null) {

       }
       else{
           holder.role.text ="/"+dataList[position].job
       }
        holder.part.text=dataList[position].field
        Glide.with(ctx)
                .load(dataList[position].image)
                .into(holder.image)

        if(dataList[position].coworkingChk==true){
            holder.coworking.setText("on")
            holder.coworking.setBackground(ContextCompat.getDrawable(ctx, R.drawable.box_on))
        }
        else{
            holder.coworking.setText("off")
            holder.coworking.setBackground(ContextCompat.getDrawable(ctx, R.drawable.box_off))

        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tv_item_directory_searching_profile_name) as TextView
        val team_name : TextView = itemView.findViewById(R.id.tv_item_directory_searching_team_name) as TextView
        val role : TextView = itemView.findViewById(R.id.tv_item_directory_searching_role) as TextView
        val part : TextView = itemView.findViewById(R.id.tv_item_directory_searching_part) as TextView
        val image : ImageView= itemView.findViewById(R.id.iv_item_directory_searching_profile_img) as ImageView
        val coworking : TextView = itemView.findViewById(R.id.tv_item_directory_searching_available_coworker) as TextView
    }
}