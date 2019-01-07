package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryBoardData
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryData
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.toast
import java.util.ArrayList

class DirectoryBoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<DirectoryData>)
    : RecyclerView.Adapter<DirectoryBoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_directory_user_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.company.text =dataList[position]!!.company
        holder.job.text = dataList[position]!!.job
        holder.time.text = dataList[position]!!.writeTime
        holder.contents_text.text = dataList[position]!!.introduce[0].content// 자기소기개 여러개 ???
        holder.name.text = dataList[position]!!.name
        Glide.with(ctx)
                .load(dataList[position].image)
                .into(holder.profile_img)

       Glide.with(ctx)
                .load(dataList[position].introduce[0].imgs[0]) //나중에 뷰페이저로 수정해야한다 .
                .into(holder.contents_img)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var time : TextView = itemView.findViewById(R.id.tv_item_directory_time) as TextView
        //val contents_img : TextView = itemView.findViewById(R.id.)
        //val contents_more : TextView = itemView.findViewById(R.id.tv_item_directory_contents_more) as TextView //boolean
        var profile_img : ImageView= itemView.findViewById(R.id.iv_item_directory_profile_img)as ImageView
        var contents_img : ImageView = itemView.findViewById(R.id.iv_item_directory_contents_image) as ImageView
        var name : TextView = itemView.findViewById(R.id.tv_item_directory_profile_name) as TextView
        var contents_text : TextView = itemView.findViewById(R.id.tv_item_directory_contents) as TextView
        var company : TextView = itemView.findViewById(R.id.tv_item_directory_company) as TextView
        var job : TextView = itemView.findViewById(R.id.tv_item_directory_job) as TextView
        //공유하기 val
        //더보기 val
    }
}