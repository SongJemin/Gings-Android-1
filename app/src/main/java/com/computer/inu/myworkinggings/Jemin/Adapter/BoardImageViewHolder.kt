package com.computer.inu.myworkinggings.Jemin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.computer.inu.myworkinggings.R

class BoardImageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    var boardImageView: ImageView = itemView!!.findViewById(R.id.item_board_img)
    var boardImageDeleteBtn : Button = itemView!!.findViewById(R.id.item_image_board_delete_btn)
}
