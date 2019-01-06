package com.computer.inu.myworkinggings.Jemin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.image

class GuestBoardAdapter(var ctx : Context  ,val guestBoardItems : ArrayList<GuestBoardItem>) : RecyclerView.Adapter<GuestBoardViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestBoardViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_guest_board, parent, false)
        return GuestBoardViewHolder(mainView)
    }

    override fun getItemCount(): Int = guestBoardItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: GuestBoardViewHolder, position: Int) {
        holder.guestName.text = guestBoardItems[position].guestModelUser.name
        holder.guestRole.text = guestBoardItems[position].guestModelUser.job
        holder.guestContent.text = guestBoardItems[position].content
        holder.guestTeam.text =  guestBoardItems[position].guestModelUser.company
        holder.guestTime.text = guestBoardItems[position].time
        Glide.with(ctx)
                .load(guestBoardItems[position].guestModelUser.image)
                .into(holder.guestImage)
    }
}