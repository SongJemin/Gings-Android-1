package com.computer.inu.myworkinggings.Jemin.Adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.R

class GetImageUrlAdapter(var ctx : Context, var boardImageItem : ArrayList<String>, var requestManager : RequestManager) : RecyclerView.Adapter<BoardImageViewHolder>() {
    var deleteImageUrl = ArrayList<String>()

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardImageViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_board_image, parent, false)
        getImageUrlAdapter = this
        return BoardImageViewHolder(mainView)
    }

    override fun getItemCount(): Int = boardImageItem.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: BoardImageViewHolder, position: Int) {
        requestManager.load(boardImageItem[position]).centerCrop().into(holder.boardImageView)
        holder.boardImageDeleteBtn.setOnClickListener {
            Toast.makeText(ctx, "지우는 버튼 클릭", Toast.LENGTH_LONG).show()
            Log.v("asdf","지우는 버튼 클릭")

            Log.v("asdf","사진" + position + "번째 지우는 버튼 클릭")
            // 삭제되는 아이템의 포지션을 가져온다
            //val position = viewHolder.getAdapterPosition()
            // 데이터의 해당 포지션을 삭제한다

            deleteImageUrl.add(boardImageItem[position])
            boardImageItem.removeAt(position)
            notifyItemRemoved(position)

        }

    }
    companion object {
        lateinit var getImageUrlAdapter: GetImageUrlAdapter
    }
}