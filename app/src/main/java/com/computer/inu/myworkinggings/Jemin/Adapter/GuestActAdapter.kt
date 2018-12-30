package com.computer.inu.myworkinggings.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Data.GuestActItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R

class GuestActAdapter(private var guestActItems : ArrayList<GuestActItem>) : RecyclerView.Adapter<GuestActViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestActViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_act_board, parent, false)
        return GuestActViewHolder(mainView)
    }

    override fun getItemCount(): Int = guestActItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: GuestActViewHolder, position: Int) {
        holder.actTitle.text = guestActItems[position].actTitle
        holder.actHashTag.text = guestActItems[position].actHashTag
        holder.actTime.text = guestActItems[position].actTime
        holder.actContent.text = guestActItems[position].actContent
        holder.actName.text = guestActItems[position].actName
        holder.actRole.text = guestActItems[position].actRole
    }
}