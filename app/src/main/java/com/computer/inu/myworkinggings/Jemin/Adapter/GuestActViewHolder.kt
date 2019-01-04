package com.computer.inu.myworkinggings.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.computer.inu.myworkinggings.R

class GuestActViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var actTitle: TextView = itemView!!.findViewById(R.id.item_act_title_tv)
    var actHashTag: TextView = itemView!!.findViewById(R.id.item_act_hashtag_tv)
    var actTime: TextView = itemView!!.findViewById(R.id.item_act_time_tv)
    var actContent: TextView = itemView!!.findViewById(R.id.item_act_content_tv)
    var actName: TextView = itemView!!.findViewById(R.id.item_act_name_tv)
    var actRole: TextView = itemView!!.findViewById(R.id.item_act_role_role_tv)
}