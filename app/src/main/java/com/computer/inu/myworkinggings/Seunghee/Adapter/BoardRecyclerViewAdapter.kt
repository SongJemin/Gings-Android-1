package com.computer.inu.myworkinggings.Seunghee.Adapter

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.computer.inu.myworkinggings.Seunghee.Post.PostBoardShareResponse
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
import com.computer.inu.myworkinggings.Moohyeon.Data.OnItemClick
import android.R.attr.onClick
import android.content.Intent
import android.widget.*


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

        holder.item_box

        //인스턴스 객체 - 데이터 연결

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
            sendLink(dataList[position])

            getBoardShareResponse()
        }

        /*이벤트 처리*/

        //디테일 보드 창으로 넘어가기
        holder.gotoDetailedBoard.setOnClickListener {

            ctx.toast(dataList[position].boardId!!.toString())
            ctx.startActivity<DetailBoardActivity>("BoardId" to dataList[position].boardId)

        }


        //더보기 버튼 클릭 시
        holder.more_btn.setOnClickListener {

            ctx.toast(dataList[position].boardId!!.toString())

            //본인 게시글 클릭
            ctx.startActivity<HomeBoardMoreBtnMineActivity>("BoardId" to dataList[position].boardId)

            //일반 게시글 클릭
            ctx.startActivity<HomeBoardMoreBtnActivity>("BoardId" to dataList[position].boardId)
        }

        //댓글 버튼
        holder.comment_btn.setOnClickListener {
            var intent = Intent(ctx, DetailBoardActivity::class.java)
            intent.putExtra("BoardId", dataList[position].boardId)
            ctx.startActivity(intent)
        }

        //좋아요 버튼
        if(dataList[position].likeChk==true)
        {
            Log.v("like_on","on error")
        holder.like_btn.setBackgroundResource(R.drawable.ic_like_on)}
        else {
            Log.v("like_off","off error")
            holder.like_btn.setBackgroundResource(R.drawable.ic_like)
        }

        holder.like_rl.setOnClickListener {
            val postBoardLikeResponse = networkService.postBoardLikeResponse("application/json",
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                    dataList[position].boardId)

            postBoardLikeResponse.enqueue(object : Callback<PostBoardLikeResponse> {
                override fun onFailure(call: Call<PostBoardLikeResponse>, t: Throwable) {
                    Log.e("통신 fail", t.toString())
                }

                override fun onResponse(call: Call<PostBoardLikeResponse>, response: Response<PostBoardLikeResponse>) {
                    if (response.isSuccessful) {

                        Log.e("통신성공","  통신 성공")
                        if(response.body()!!.message=="보드 추천 성공"){
                            ctx.toast("좋아요 성공")
                            holder.like_btn.setBackgroundResource(R.drawable.ic_like_on)
                            var cnt =Integer.parseInt(holder.like_cnt.getText().toString())+1
                            holder.like_cnt.setText(cnt.toString())
                        }
                        else if (response.body()!!.message=="보드 추천 해제 성공"){
                            ctx.toast("좋아요 해제")
                            holder.like_btn.setBackgroundResource(R.drawable.ic_like)
                            var cnt =Integer.parseInt(holder.like_cnt.getText().toString())-1

                            holder.like_cnt.setText(cnt.toString())
                        }
                    }
                }

            })
        }
        //댓글창=> 디테일보드
    }


    //layout의 view를 인스턴스 변수로 만들어 줌
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item_box : LinearLayout = itemView.findViewById(R.id.btn_rv_item_board_box) as LinearLayout
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
        var like_rl : RelativeLayout = itemView.findViewById(R.id.rl_item_board_like) as RelativeLayout
        var like_btn: Button = itemView.findViewById(R.id.iv_item_like_btn) as Button
        var like_cnt: TextView = itemView.findViewById(R.id.tv_item_board_like_cnt) as TextView //int

        //댓글아이콘
        val comment_btn: RelativeLayout = itemView.findViewById(R.id.rl_item_board_comment) as RelativeLayout
        val comment_cnt: TextView = itemView.findViewById(R.id.tv_item_board_comment_cnt) as TextView //int

        //공유하기 val
        val share_btn: ImageView = itemView.findViewById(R.id.iv_item_board_share) as ImageView

        val more_btn: Button = itemView.findViewById(R.id.btn_rv_item_more) as Button

    }


    //보드공유 통신
    private fun getBoardShareResponse(){
        val postBoardshareResponse = networkService.postBoardShareResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                b_id)

        postBoardshareResponse.enqueue(object : Callback<PostBoardShareResponse> {
            override fun onFailure(call: Call<PostBoardShareResponse>, t: Throwable) {
                Log.e("보드공유 통신 fail", t.toString())
            }

            override fun onResponse(call: Call<PostBoardShareResponse>, response: Response<PostBoardShareResponse>) {
                if (response.isSuccessful) {
                    Log.e("보드공유 통신성공", "  통신 성공")
                    ctx.toast("공유@")
                }
            }
        })

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

    //카카오톡 링크 공유
    private fun sendLink(dataList: BoardItem) {


        var images : String?

        Log.v("ㅎ;;ㅎ;ㅎ;;ㅎ;ㅎㅎ 너의크기는무엇이늬",dataList.images.size.toString())

        if(dataList.images.size == 0){
            images = "https://s3.ap-northeast-2.amazonaws.com/gings-storage/gings.png"
        }else
            images = dataList.images[0]

        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(dataList.title,
                        images,
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("혁신적인 창업가들을 위한 멤버쉽 커뮤니티, 깅스")

                        .build())

                .addButton(ButtonObject("깅스 앱으로 열기", LinkObject.newBuilder()
                        //.setWebUrl("'https://developers.kakao.com")
                        .setAndroidExecutionParams("boardIDValue="+dataList.boardId)
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(ctx, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {
                Logger.e(errorResult.toString())
            }
            override fun onSuccess(result: KakaoLinkResponse) {}
        })
    }
}




