package com.computer.inu.myworkinggings.Seunghee.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.HomeBoardMoreBtnActivity
import com.computer.inu.myworkinggings.Seunghee.Activity.HomeBoardMoreBtnMineActivity
import com.computer.inu.myworkinggings.data.BoardData
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class BoardRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<BoardData>)
    :RecyclerView.Adapter<BoardRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_board, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //'인스턴스 객체 = 데이터' 연결

        //title
        holder.category.text = dataList[position].category
        holder.title.text = dataList[position].title
        holder.tag.text = dataList[position].tag
        holder.time.text = dataList[position].time

        //contents
        //holder.contents_img
        holder.contents_text.text = dataList[position].contents_text

        //profile
        holder.name.text = dataList[position].name
        holder.team.text = dataList[position].team
        holder.role.text = dataList[position].role

        //
        holder.like_cnt.text = dataList[position].like_cnt.toString()
        holder.comment_cnt.text = dataList[position].comment_cnt.toString()


        /*이벤트 처리*/

        //디테일 보드 창으로 넘어가기
        holder.gotoDetailedBoard.setOnClickListener {
            ctx.startActivity<DetailBoardActivity>()
        }

        //더보기 버튼 클릭 시
        holder.more_btn.setOnClickListener {

            //본인 게시글 클릭
            ctx.startActivity<HomeBoardMoreBtnMineActivity>()

            //일반 게시글 클릭
            ctx.startActivity<HomeBoardMoreBtnActivity>()
        }

        //좋아요 버튼

        //댓글창=> 디테일보드


    }


    //layout의 view를 인스턴스 변수로 만들어 줌
    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){

        //
        val gotoDetailedBoard : LinearLayout = itemView.findViewById(R.id.ll_item_board_list_contents) as LinearLayout

        //title
        val category : TextView = itemView.findViewById(R.id.tv_item_board_category) as TextView
        val title : TextView = itemView.findViewById(R.id.tv_item_board_title) as TextView
        val tag : TextView  = itemView.findViewById(R.id.tv_item_board_tag1) as TextView
        val time : TextView = itemView.findViewById(R.id.tv_item_board_time) as TextView

        //contents
        val contents_img : ImageView = itemView.findViewById(R.id.iv_item_board_contents_image) as ImageView
        val contents_text : TextView = itemView.findViewById(R.id.tv_item_board_title) as TextView
        val contents_more : TextView = itemView.findViewById(R.id.tv_item_board_contents_more) as TextView

        //프로필
        //val profile_img : ImageView = itemView.findViewById(R.id.iv_home_board_profile_img) as ImageView
        val name : TextView = itemView.findViewById(R.id.tv_item_board_profile_name) as TextView
        val team : TextView = itemView.findViewById(R.id.tv_item_board_profile_team) as TextView
        val role : TextView = itemView.findViewById(R.id.tv_item_board_profile_role) as TextView

        //좋아요
        val like_btn : ImageView = itemView.findViewById(R.id.iv_item_board_like) as ImageView
        val like_cnt : TextView = itemView.findViewById(R.id.tv_item_board_like_cnt) as TextView //int

        //댓글아이콘
        val comment_btn : ImageView = itemView.findViewById(R.id.iv_item_board_comment) as ImageView
        val comment_cnt : TextView = itemView.findViewById(R.id.tv_item_board_comment_cnt) as TextView //int

        //공유하기 val
        val share_btn : ImageView = itemView.findViewById(R.id.iv_item_board_share) as ImageView

        //더보기 val
        val more_btn : ImageView = itemView.findViewById(R.id.iv_item_board_more_btn) as ImageView
    }
}