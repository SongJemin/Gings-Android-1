package com.computer.inu.myworkinggings.Jemin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Jemin.Data.GuestActItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.GetActiveData
import com.computer.inu.myworkinggings.R

class GuestActAdapter(var ctx : Context, private var guestActItems : ArrayList<GetActiveData>) : RecyclerView.Adapter<GuestActViewHolder>() {

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestActViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_act_board, parent, false)
        return GuestActViewHolder(mainView)
    }

    override fun getItemCount(): Int = guestActItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: GuestActViewHolder, position: Int) {
        holder.actCategory.text = guestActItems[position].category
        if (guestActItems[position].category == "QUESTION") {
            holder.actCategory.text = "질문"
        } else if (guestActItems[position].category == "INSPIRATION") {
            holder.actCategory.text = "영감"
        } else if (guestActItems[position].category == "COWORKING") {
            holder.actCategory.text = "협업"
        }

        holder.actTitle.text = guestActItems[position].title
        for (i in 0..guestActItems[position].keywords.size - 1) {
            if (i == 0) {
                holder.actHashTag.text = "#" + guestActItems[position].keywords[i]
            } else {
                holder.actHashTag.append("    #" + guestActItems[position].keywords[i])
            }
        }
        holder.actTime.text = guestActItems[position].time!!.substring(0,16).replace("T"," ")
        holder.actContent.text = guestActItems[position].content
        holder.actName.text = guestActItems[position].writer
        if(guestActItems[position].category == "QUESTION") {
            holder.actRole.text = "질문"
        }
        else if(guestActItems[position].category == "INSPIRATION") {
            holder.actRole.text = "영감"
        }
        else if(guestActItems[position].category == "COWORKING"){
            holder.actRole.text = "협업"
        }
        holder.actReplyRecom.text = guestActItems[position].recommender!!.toString()
        holder.actReplySumNum.text = guestActItems[position].replys.size.toString()
        if(guestActItems[position].images.size != 0){
            Glide.with(ctx).load(guestActItems[position].images[0]).into(holder.actBackgroundImg)
        }
    }
}