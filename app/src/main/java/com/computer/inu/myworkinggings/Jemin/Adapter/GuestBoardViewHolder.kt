package com.computer.inu.myworkinggings.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.computer.inu.myworkinggings.R

class GuestBoardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var guestName: TextView = itemView!!.findViewById(R.id.item_guest_name_tv)
    var guestRole: TextView = itemView!!.findViewById(R.id.item_guest_role_tv)
    var guestDate: TextView = itemView!!.findViewById(R.id.item_guest_date_tv)
    var guestTime: TextView = itemView!!.findViewById(R.id.item_guest_time_tv)
    var guestContent: TextView = itemView!!.findViewById(R.id.item_guest_content_tv)
}