package com.computer.inu.myworkinggings.Jemin.Adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Data.ChatListItem
import com.computer.inu.myworkinggings.R

class ChatAdapter (private var chatListItem: ArrayList<ChatListItem>) : RecyclerView.Adapter<ChatViewHolder>(){

    val TAG = "ChatAdapter"
    var userID : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_view, parent, false)
        return ChatViewHolder(mainView)
    }

    override fun getItemCount(): Int = chatListItem.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        // 내가 보낸 채팅일 경우
        if(chatListItem[position]!!.ChatUserID == 0)
        {
            Log.v(TAG, "보낸 메세지")
            holder.chatLeftTime.visibility = View.VISIBLE
            holder.chatRightTime.visibility = View.GONE
           // holder.chatContent.setBackgroundResource(R.drawable.chat_purple)
            holder.chatProfileLayout.visibility = View.GONE
            holder.chatUserName.visibility = View.GONE
            holder.chatChatLayout.gravity = Gravity.END
            holder.chatMineViewLayout.visibility = View.VISIBLE
            //requestManager.load(chatListItem[position]!!.ChatUserImgUrl).centerCrop().into(holder.chatUserImg)
            holder.chatUserName.text = "보낸사람"
            holder.chatContent.text = chatListItem[position]!!.ChatContent
            holder.chatLeftTime.text = "00:06"
            holder.chatContent.setTextColor(Color.BLACK)
            holder.chatGapLayout.visibility = View.GONE
        }

        // 남이 보낸 채팅일 경우
        else{
            Log.v(TAG, "받은 메세지")
            holder.chatLeftTime.visibility = View.GONE
            // 두번째 메세지부터
            if(position > 0){


                    holder.chatUserImg.visibility = View.VISIBLE
                   // requestManager.load(chatListItem[position]!!.ChatUserImgUrl).centerCrop().into(holder.chatUserImg)
                    holder.chatGapLayout.visibility = View.VISIBLE
                    holder.chatGap2Layout.visibility = View.VISIBLE

            }
            // 해당 채팅방에서 첫번째 대화 내용일경우
            else{
                holder.chatUserImg.visibility = View.VISIBLE
                //requestManager.load(chatListItem[position]!!.ChatUserImgUrl).centerCrop().into(holder.chatUserImg)
                holder.chatGapLayout.visibility = View.GONE
                holder.chatGap2Layout.visibility = View.VISIBLE
            }

            holder.chatRightTime.visibility = View.VISIBLE
           // holder.chatContent.setBackgroundResource(R.drawable.chat_gray)
            holder.chatMainLayout.gravity = Gravity.LEFT
            holder.chatMineViewLayout.visibility = View.GONE

            holder.chatUserName.text = "받는사람"
            holder.chatContent.text = chatListItem[position]!!.ChatContent
            holder.chatRightTime.text = "00:06"
        }
        holder.setIsRecyclable(false)   // 리사이클러뷰 재사용 금지
    }
}