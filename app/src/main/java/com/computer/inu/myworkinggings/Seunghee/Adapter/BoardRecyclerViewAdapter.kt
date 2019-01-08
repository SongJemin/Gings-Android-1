package com.computer.inu.myworkinggings.Seunghee.Adapter

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Adapter.ImageAdapter
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.Moohyeon.post.PostBoardLikeResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.HomeBoardMoreBtnActivity
import com.computer.inu.myworkinggings.Seunghee.Activity.HomeBoardMoreBtnMineActivity
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.log.Logger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class BoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<BoardItem>, var requestManager: RequestManager)
    : RecyclerView.Adapter<BoardRecyclerViewAdapter.Holder>() {
    var b_id: Int = 0
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_board, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        //인스턴스 객체 - 데이터 연결

        b_id = dataList[position].boardId!!
        //title
        dataList
        holder.category.text = dataList[position].category
        holder.title.text = dataList[position].title
        for (i in 0..dataList[position].keywords.size - 1) {
            if (i == 0) {
                holder.tag.text = "#" + dataList[position].keywords[i]
            } else {
                holder.tag.append("    #" + dataList[position].keywords[i])
            }
        }
        for (i in 0..dataList[position].images.size - 1) {
            if (dataList[position].images.size == 0) {
                Log.v("asdf", "사이즈 0" + dataList[position].images.size)
                holder.contents_img_viewPager.visibility = View.GONE
            } else {
                Log.v("asdf", "사이즈 있음 " + dataList[position].images.size)
                var adapter = ImageAdapter(ctx, requestManager, dataList[position].images)
                holder.contents_img_viewPager.adapter = adapter
                if (dataList[position].images[i] == "abcd") {
                    holder.contents_img_viewPager.visibility = View.GONE
                }
            }
        }

        holder.time.text = dataList[position].time!!.substring(0, 16).replace("T", " ")

        //contents
        //holder.contents_img
        holder.contents_text.text = dataList[position].content

        //profile
        //프로필이미지 나중에확인해보기ㅣㅣㅣㅣ이ㅣㅣㅣ히ㅣㅣ
        lateinit var requestManager: RequestManager
        requestManager = Glide.with(ctx)
        requestManager.load(dataList[position]!!.writerImage).into(holder.profile_img)

        holder.name.text = dataList[position].writer
        holder.role.text = dataList[position].field
        holder.team.text = dataList[position].company

        // 좋아요 수
        holder.like_cnt.text = dataList[position].recommender!!.toString()

        // 댓글 수
        holder.comment_cnt.text = dataList[position].numOfReply.toString()


        //공유
        holder.share_btn.setOnClickListener {
            //sendLink()
        }

        /*이벤트 처리*/

        //디테일 보드 창으로 넘어가기
        holder.gotoDetailedBoard.setOnClickListener {

            ctx.toast(dataList[position].boardId!!.toString())
            ctx.startActivity<DetailBoardActivity>("BoardId" to dataList[position].boardId)

        }


        //더보기 버튼 클릭 시
        holder.more_btn.setOnClickListener {

            //본인 게시글 클릭
            ctx.toast(dataList[position].boardId!!.toString())
            ctx.startActivity<HomeBoardMoreBtnMineActivity>("BoardId" to dataList[position].boardId)

            //일반 게시글 클릭
            ctx.startActivity<HomeBoardMoreBtnActivity>()
        }

        //좋아요 버튼
        holder.like_btn.setOnClickListener {
            BoardLikePost()
        }
        //댓글창=> 디테일보드
    }


    //layout의 view를 인스턴스 변수로 만들어 줌
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gotoDetailedBoard: LinearLayout = itemView.findViewById(R.id.ll_item_board_list_contents) as LinearLayout

        //title
        val category: TextView = itemView.findViewById(R.id.tv_item_board_category) as TextView
        val title: TextView = itemView.findViewById(R.id.tv_item_board_title) as TextView
        val tag: TextView = itemView.findViewById(R.id.tv_item_board_tag) as TextView
        val time: TextView = itemView.findViewById(R.id.tv_item_board_time) as TextView

        //contents
        var contents_img_viewPager: ViewPager = itemView.findViewById<ViewPager>(R.id.iv_item_board_contents_image_viewpager)
        val contents_text: TextView = itemView.findViewById(R.id.tv_item_board_contents_text) as TextView
        val contents_more: TextView = itemView.findViewById(R.id.tv_item_board_contents_more) as TextView

        //프로필
        val profile_img: ImageView = itemView.findViewById(R.id.iv_item_board_profile_img) as ImageView
        val name: TextView = itemView.findViewById(R.id.tv_item_board_profile_name) as TextView
        val team: TextView = itemView.findViewById(R.id.tv_item_board_profile_team) as TextView
        val role: TextView = itemView.findViewById(R.id.tv_item_board_profile_role) as TextView

        //좋아요
        val like_btn: ImageView = itemView.findViewById(R.id.iv_item_board_like) as ImageView
        val like_cnt: TextView = itemView.findViewById(R.id.tv_item_board_like_cnt) as TextView //int

        //댓글아이콘
        val comment_btn: ImageView = itemView.findViewById(R.id.iv_item_board_comment) as ImageView
        val comment_cnt: TextView = itemView.findViewById(R.id.tv_item_board_comment_cnt) as TextView //int

        //공유하기 val
        val share_btn: ImageView = itemView.findViewById(R.id.iv_item_board_share) as ImageView

        val more_btn: Button = itemView.findViewById(R.id.btn_rv_item_more) as Button
    }


    private fun BoardLikePost() {
        val postBoardLikeResponse = networkService.postBoardLikeResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                b_id)

        postBoardLikeResponse.enqueue(object : Callback<PostBoardLikeResponse> {
            override fun onFailure(call: Call<PostBoardLikeResponse>, t: Throwable) {
                Log.e("통신 fail", t.toString())
            }

            override fun onResponse(call: Call<PostBoardLikeResponse>, response: Response<PostBoardLikeResponse>) {
                if (response.isSuccessful) {
                    Log.e("통신성공", "  통신 성공")
                }
            }
        })
    }

/*    private fun sendLink() {
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("카카오~!",
                        "http://www.trinityseoul.com/uploads/8/7/6/4/87640636/art-talk-20_orig.jpg",
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("깅스에 초대받으셨습니다.")
                        .build())

                .addButton(ButtonObject("깅스 앱으로 열기", LinkObject.newBuilder()
                        //.setWebUrl("'https://developers.kakao.com")
                        //.setAndroidExecutionParams("roomIDValue="+roomIDValue)
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(ctx, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {
                Logger.e(errorResult.toString())
            }

            override fun onSuccess(result: KakaoLinkResponse) {}
        })


    }*/
}




