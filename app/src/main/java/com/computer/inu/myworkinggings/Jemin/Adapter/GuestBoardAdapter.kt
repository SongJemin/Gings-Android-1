package com.computer.inu.myworkinggings.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R

class GuestBoardAdapter(private var guestBoardItems : ArrayList<GuestBoardItem>) : RecyclerView.Adapter<GuestBoardViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestBoardViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_guest_board, parent, false)
        return GuestBoardViewHolder(mainView)
    }

    override fun getItemCount(): Int = guestBoardItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: GuestBoardViewHolder, position: Int) {
        holder.guestName.text = guestBoardItems[position].guestName
        holder.guestRole.text = guestBoardItems[position].guestRole
        holder.guestDate.text = guestBoardItems[position].guestDate
        holder.guestTime.text = guestBoardItems[position].guestTime
        holder.guestContent.text = guestBoardItems[position].guestContent
    }
}