package com.computer.inu.myworkinggings.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.data.BoardData
import java.util.ArrayList

class BoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<BoardData>)
    :RecyclerView.Adapter<BoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_board, parent, false)



        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.category.text = dataList[position].category
        holder.title.text = dataList[position].title
        holder.tag.text = dataList[position].tag
        holder.time.text = dataList[position].time
        holder.contents_text.text = dataList[position].contents_text
        //holder.contents_more.text = dataList[position].contents_more
        holder.name.text = dataList[position].name
        holder.team.text = dataList[position].team
        holder.role.text = dataList[position].role
        holder.like_cnt.text = dataList[position].like_cnt.toString()
        holder.comment_cnt.text = dataList[position].comment_cnt.toString()
    }


    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val category : TextView = itemView.findViewById(R.id.tv_item_board_category) as TextView
        val title : TextView = itemView.findViewById(R.id.tv_item_board_title) as TextView
        val tag : TextView  = itemView.findViewById(R.id.tv_item_board_tag1) as TextView //태그 다섯개ㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐㅐ
        val time : TextView = itemView.findViewById(R.id.tv_item_board_time) as TextView
        //val contents_img : TextView = itemView.findViewById(R.id.)
        val contents_text : TextView = itemView.findViewById(R.id.tv_item_board_title) as TextView
        //val contents_more : TextView = itemView.findViewById(R.id.tv_item_board_contents_more) as TextView //boolean
        //val profile_img : TextView
        val name : TextView = itemView.findViewById(R.id.tv_item_board_profile_name) as TextView
        val team : TextView = itemView.findViewById(R.id.tv_item_board_profile_team) as TextView
        val role : TextView = itemView.findViewById(R.id.tv_item_board_profile_role) as TextView
        //추천아이콘 val
        val like_cnt : TextView = itemView.findViewById(R.id.tv_item_board_like_cnt) as TextView //int
        //댓글아이콘 val
        val comment_cnt : TextView = itemView.findViewById(R.id.tv_item_board_comment_cnt) as TextView //int
        //공유하기 val
        //더보기 val
    }
}