package com.computer.inu.myworkinggings.Jemin.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.MypageUpdateActivity
import com.computer.inu.myworkinggings.Jemin.Data.BoardImageItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.UpBoardActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class BoardImageAdapter(var ctx : Context, private var boardImageItem : ArrayList<Uri>, var requestManager : RequestManager, var insertFlag : Int) : RecyclerView.Adapter<BoardImageViewHolder>() {

    var checkFlag : Int = 0 // 0 = 업보드 등록, 1 = 마이페이지 등록

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

        checkFlag = insertFlag
        holder.boardImageDeleteBtn.setOnClickListener {
            Toast.makeText(ctx, "지우는 버튼 클릭", Toast.LENGTH_LONG).show()
            Log.v("asdf","사진" + position + "번째 지우는 버튼 클릭")
            // 삭제되는 아이템의 포지션을 가져온다
            //val position = viewHolder.getAdapterPosition()
            // 데이터의 해당 포지션을 삭제한다

            if(checkFlag == 0){
                UpBoardActivity.upBoardActivity.imagesList.removeAt(position)
                Log.v("asdf","업보드 쪽 실행")
            }
            if(checkFlag == 1){
                MypageUpdateActivity.mypageUpdateActivity.imagesList.removeAt(position)
                Log.v("asdf","자기소개 쪽 실행")
            }


            boardImageItem.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}