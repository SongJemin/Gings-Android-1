package com.computer.inu.myworkinggings.Jemin.Adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Data.BoardImageItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R

class BoardImageAdapter(private var boardImageItem : ArrayList<Uri>, var requestManager : RequestManager) : RecyclerView.Adapter<BoardImageViewHolder>() {


    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardImageViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_board_image, parent, false)
        return BoardImageViewHolder(mainView)
    }

    override fun getItemCount(): Int = boardImageItem.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: BoardImageViewHolder, position: Int) {
        requestManager.load(boardImageItem[position]).centerCrop().into(holder.boardImageView)
    }
}