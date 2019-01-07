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
import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser
import com.computer.inu.myworkinggings.Jemin.Activity.MypageUpdateActivity
import com.computer.inu.myworkinggings.Jemin.Data.ImageType
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.UpBoardActivity


class BoardImageAdapter(var boardImageItem : ArrayList<ImageType>, var requestManager : RequestManager, var insertFlag : Int, var insertUrlorUri : Int, var insertImageUrlSize : Int) : RecyclerView.Adapter<BoardImageViewHolder>() {

    var checkFlag : Int = 0 // 0 = 업보드 등록, 1 = 마이페이지 등록
    var checkUrlorUri : Int = 0 // 0 = url값, 1 = uri값
    var getImageUrlSize : Int = 0
    var urlRemovedCount : Int = 0
    var deleteImageUrlList = ArrayList<String>()

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardImageViewHolder {

        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_board_image, parent, false)
        boardImageAdapter = this
        return BoardImageViewHolder(mainView)
    }

    override fun getItemCount(): Int = boardImageItem.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: BoardImageViewHolder, position: Int) {
        checkUrlorUri = insertUrlorUri
        getImageUrlSize = insertImageUrlSize
        checkFlag = insertFlag

        // 서버로부터 받은 이미지 리스트 개수가 0이면
        if(getImageUrlSize == 0){
            // 차례대로 이미지 url을 통해서 리사이클러뷰에 등록
            requestManager.load(boardImageItem[getImageUrlSize+position].imageUri).centerCrop().into(holder.boardImageView)
        }
        // 서버로부터 받은 이미지 리스트 개수가 0보다 크다면
        else{
            // 서버로부터 받은 이미지들부터 먼저 리사이클러뷰에 등록
            if(position <= getImageUrlSize-1-urlRemovedCount) {
                // 차례대로 이미지 url을 통해서 리사이클러뷰에 등록
                requestManager.load(boardImageItem[position].imageUrl).centerCrop().into(holder.boardImageView)
            }
            // 그다음 갤러리에서 추가된 이미지 리스트 리사이클러뷰에 등록
            else{
                // 차례대로 이미지 uri를 통해서 리사이클러뷰에 등록
                requestManager.load(boardImageItem[position].imageUri).centerCrop().into(holder.boardImageView)
            }
        }

        // 특정 사진 삭제 버튼  클릭시
        holder.boardImageDeleteBtn.setOnClickListener {

            // 업보드쪽
            if(checkFlag == 0){
                deleteImageUrlList.add(boardImageItem[position].imageUrl!!)
                Log.v("asdf","해당 이미지 url 제거 " + boardImageItem[position].imageUrl)
                boardImageItem.removeAt(position)

                // 나중에 다시 추가 UpBoardActivity.upBoardActivity.imagesList.removeAt(position)
            }
            // 자기소개쪽
            if(checkFlag == 1){
                MypageUpdateActivity.mypageUpdateActivity.imagesList.removeAt(position)
            }

            // 서버로부터 받은 사진 지우기
            if(position <= getImageUrlSize-1-urlRemovedCount){
                Log.v("ASdf","이미지 삭제 + url")

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, boardImageItem.size);
                urlRemovedCount += 1
                Log.v("ASdf","url 이미지 삭제 카운트 = " + urlRemovedCount)
                //boardImageItem.removeAt(position)
                //notifyItemRemoved(position)
            }
            // 갤러리에서 올린 사진 지우기
            else{
                Log.v("ASdf","이미지 삭제 + uri")
                Log.v("ASdf","이미지 삭제 포지션 = " + position)
                UpBoardActivity.upBoardActivity.imagesList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, boardImageItem.size);
            }
        }
    }

    companion object {
        lateinit var boardImageAdapter: BoardImageAdapter
    }

}
