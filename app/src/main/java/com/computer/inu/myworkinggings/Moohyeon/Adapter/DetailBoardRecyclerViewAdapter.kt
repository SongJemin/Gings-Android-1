package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.ReboardMoreBtnMineActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.GET.ReplyData
import com.computer.inu.myworkinggings.Seunghee.Post.PostReboardRecommendResponse
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import android.app.Activity
import android.widget.LinearLayout
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import org.jetbrains.anko.ctx
import android.media.Image
import android.widget.RelativeLayout


class DetailBoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ReplyData?>)
    : RecyclerView.Adapter<DetailBoardRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_detailboard, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.reboardBottomLayout.visibility = View.GONE
        if(position == dataList.size-1){
            holder.reboardBottomLayout.visibility = View.VISIBLE
        }
        holder.name.text = dataList[position]!!.writer
        holder.time.text = dataList[position]!!.writeTime
        holder.contents_text.text = dataList[position]!!.content
        holder.reboard_like_cnt.text = dataList[position]!!.recommender.toString()
        Glide.with(ctx).load(dataList[position]!!.writerImage).into(holder.profileImg)


        if (dataList[position]!!.likeChk!!) {

            holder.reboard_like_img.visibility = View.GONE
            holder.reboard_like_img_red.visibility = View.VISIBLE

        } else {
            holder.reboard_like_img_red.visibility = View.GONE
            holder.reboard_like_img.visibility = View.VISIBLE
        }


        //댓글 좋아요
        holder.reboard_like_layout.setOnClickListener {

            val networkService: NetworkService by lazy {
                ApplicationController.instance.networkService
            }

            //댓글좋아요
            val postReBoardrecommendResponse = networkService.postReboardRecommendResponse("application/json",
                    SharedPreferenceController.getAuthorization(ctx),
                    dataList[position]!!.replyId!!
            )
            postReBoardrecommendResponse.enqueue(object : Callback<PostReboardRecommendResponse> {
                override fun onFailure(call: Call<PostReboardRecommendResponse>, t: Throwable) {
                    Log.e("sign up fail", t.toString())
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<PostReboardRecommendResponse>, response: Response<PostReboardRecommendResponse>) {
                    if (response.isSuccessful) {
                        //holder.reboard_like_cnt

                        if (response.body()!!.message == "리보드 추천 해제 성공") {
                            //좋아요 해재

                            holder.reboard_like_img_red.visibility = View.GONE
                            holder.reboard_like_img.visibility = View.VISIBLE

                            //인트형으로 바꾸기
                            var cnt =Integer.parseInt(holder.reboard_like_cnt.getText().toString())-1
                            holder.reboard_like_cnt.setText(cnt.toString())

                        } else {
                            //좋아요

                            holder.reboard_like_img.visibility = View.GONE
                            holder.reboard_like_img_red.visibility = View.VISIBLE

                            var cnt =Integer.parseInt(holder.reboard_like_cnt.getText().toString())+1
                            holder.reboard_like_cnt.setText(cnt.toString())
                        }
                    }
                }
            })
        }

        holder.reboardMoreImg.setOnClickListener {
            var intent = Intent(ctx, ReboardMoreBtnMineActivity::class.java)
            intent.putExtra("reboardId", dataList[position]!!.replyId)
            Log.v("asdf","선택 리보드 ID 값 = " + dataList[position]!!.replyId)

            (ctx as Activity).startActivityForResult(intent, 30)
        }

        //이미지
        lateinit var requestManager: RequestManager
        requestManager = Glide.with(ctx)

        //사진이 하나밖에 없을 경우,
        if( dataList[position]!!.images.size == 0 )
            holder.contents_images.visibility = View.GONE
        else{
            for (i in 0..dataList[position]!!.images.size - 1)
                requestManager.load(dataList[position]!!.images[0]).into(holder.contents_images)
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val profileImg : ImageView = itemView.findViewById(R.id.iv_item_detailboard_profile_img)
        val time: TextView = itemView.findViewById(R.id.tv_item_detailboard_time) as TextView
        val name: TextView = itemView.findViewById(R.id.tv_item_detailboard_profile_name) as TextView
        val contents_text: TextView = itemView.findViewById(R.id.tv_item_detailboard_contents) as TextView
        val contents_images: ImageView = itemView.findViewById(R.id.iv_item_board_image_contents) as ImageView

        val reboard_like_layout: LinearLayout = itemView.findViewById(R.id.ll_item_reboard_ike) as LinearLayout

        val reboard_like_img : ImageView = itemView.findViewById(R.id.iv_item_reboard_like) as ImageView
        val reboard_like_img_red : ImageView = itemView.findViewById(R.id.iv_item_reboard_like_red) as ImageView
        var reboard_like_cnt: TextView = itemView.findViewById(R.id.iv_item_reboard_like_cnt) as TextView
        var reboardMoreImg : ImageView = itemView.findViewById(R.id.iv_item_rebord_more) as ImageView
        var reboardBottomLayout : LinearLayout = itemView.findViewById(R.id.rv_item_detail_board_layout) as LinearLayout

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Log.d("MyAdapter", "onActivityResult")
    }

}
