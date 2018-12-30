package com.computer.inu.myworkinggings.Seunghee.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.computer.inu.myworkinggings.R
import com.sopt.gings.data.AlbumData

class AlbumRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<AlbumData>) : RecyclerView.Adapter<AlbumRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_album_image, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) { //뷰 바인딩!!

        //holder.mImg. = dataList[position].mImg
    }



    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val mImg: ImageView = itemView.findViewById(R.id.iv_item_album_image) as ImageView
    }
}
