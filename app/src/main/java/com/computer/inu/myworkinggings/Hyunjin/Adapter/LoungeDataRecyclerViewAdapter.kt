package com.computer.inu.myworkinggings.Hyunjin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Hyunjin.Activity.LoungeIntroduceActivity
import com.computer.inu.myworkinggings.Hyunjin.Get.ClubData
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity

class LoungeDataRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<ClubData>) : RecyclerView.Adapter<LoungeDataRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//뷰 인플레이트!!
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_lounge, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
//뷰 바인딩!!
        Glide.with(ctx).load(dataList[position].backImg).into(holder.backImg)
        Glide.with(ctx).load(dataList[position].backImg).centerCrop().into(holder.backImg)
        holder.title.text = dataList[position].title
        holder.all.setOnClickListener {
            ctx.startActivity<LoungeIntroduceActivity>()
        }

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val backImg : ImageView = itemView.findViewById(R.id.rl_iv_lounge_image) as ImageView
        val title : TextView = itemView.findViewById(R.id.tv_rv_item_lounge_title) as TextView
        val all : RelativeLayout = itemView.findViewById(R.id.iv_rv_item_lounge_image) as RelativeLayout
    }
}