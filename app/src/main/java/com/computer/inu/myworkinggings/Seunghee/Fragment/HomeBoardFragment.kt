package com.computer.inu.myworkinggings.Seunghee.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.CategoryMenuActivity
import com.computer.inu.myworkinggings.Seunghee.Activity.UpBoardActivity
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.GET.GetBoardSearchResponse
import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import com.computer.inu.myworkinggings.Jemin.Adapter.BoardImageAdapter
import com.computer.inu.myworkinggings.Jemin.Data.ImageType
import android.support.design.R.id.scrollView
import android.support.v4.widget.NestedScrollView
import android.widget.ScrollView
import com.computer.inu.myworkinggings.Jemin.Fragment.MyPageFragment
import kotlinx.android.synthetic.main.activity_detail_board.*
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController


class HomeBoardFragment : Fragment() {

    private var isinSearchData = false

    lateinit var searchBoardRecyclerViewAdapter: BoardRecyclerViewAdapter


    lateinit var requestManager: RequestManager
    lateinit var networkService: NetworkService

    //검색 리사이클러뷰
    lateinit var BoardDataForSearch: ArrayList<BoardData>

    companion object {
        //홈보드 리사이클러뷰
        lateinit var boardRecyclerViewAdapter: BoardRecyclerViewAdapter
    }


    var BoardItemListForSearch = ArrayList<BoardItem>()
    var BoardData = ArrayList<BoardData>()
    var BoardItemList = ArrayList<BoardItem>()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_board, container, false)
        networkService = ApplicationController.instance.networkService
        requestManager = Glide.with(this)

        getBoard()
        Log.v("토큰ㅋ",SharedPreferenceController.getAuthorization(context!!).toString())

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 당겨서 새로 고침 기능
        sr_homeboard_refresh.setOnRefreshListener {
            getBoard()
            sr_homeboard_refresh.isRefreshing=false
        }


        //최상단으로
        rl_logo_goto_top.setOnClickListener {

            /*
            rv_item_board_list_for_search.smoothScrollToPosition( 0 )
            rv_item_board_list.smoothScrollToPosition( 0 )*/

            //nested_home_board.fullScroll
            nested_home_board.smoothScrollTo(0,0)
        }

        //게시글 작성 버튼
        tv_home_board_write_board.setOnClickListener {
            startActivity<UpBoardActivity>()
        }

        //카테고리 메뉴바 열기
        iv_btn_home_board_category.setOnClickListener {
            startActivity<CategoryMenuActivity>()
        }

        iv_btn_home_board_search.setOnClickListener {

            rl_home_board_main_bar.visibility = View.GONE
            rl_home_board_main_bar_for_search.visibility = View.VISIBLE
        }

        /*검색*/
        et_home_board_search.inputType = InputType.TYPE_CLASS_TEXT
        et_home_board_search.imeOptions = EditorInfo.IME_ACTION_SEARCH

        et_home_board_search.setOnEditorActionListener({ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {

                //검색 시
                if (et_home_board_search.text.toString() == "") {
                    et_home_board_search.visibility = View.GONE
                    iv_home_board_search_fail.visibility = View.VISIBLE
                } else {
                    //검색통신
                    getHomeBoardSearchResponse(et_home_board_search.text.toString())

                    ll_home_board_board_view.visibility = View.GONE
                }

                val imm = ctx?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(et_home_board_search.getWindowToken(), 0)
            }
            handled
        })

        onSearchClickListener()

    }

    //검색클릭리스너
    private fun onSearchClickListener() {

        /*엑스버튼 누를 경우*/
        //edittext에 있는 내용들 다 지우고, 다시 메인창 띄우기
        iv_home_board_close_btn.setOnClickListener {

            //텍스트 내용지움
            et_home_board_search.text.clear()

            //상단바
            rl_home_board_main_bar_for_search.visibility = View.GONE
            rl_home_board_main_bar.visibility = View.VISIBLE

            //리사이클러뷰
            ll_home_board_board_view_for_search.visibility = View.GONE
            ll_home_board_board_view.visibility = View.VISIBLE
        }

    }

    //검색
    private fun getHomeBoardSearchResponse(text: String) {
        val getHomeboardSearchResponse = networkService.getBoardSearchResponse("application/json",
                SharedPreferenceController.getAuthorization(context!!),
                text)
        getHomeboardSearchResponse.enqueue(object : Callback<GetBoardSearchResponse> {

            override fun onFailure(call: Call<GetBoardSearchResponse>, t: Throwable) {
                Log.e("Login fail", t.toString())
            }

            override fun onResponse(call: Call<GetBoardSearchResponse>, response: Response<GetBoardSearchResponse>) {
                if (response!!.isSuccessful) {

                    if (isinSearchData == true) {

                        BoardItemListForSearch.clear()
                        BoardDataForSearch.clear()

                        searchBoardRecyclerViewAdapter.dataList.clear()

                        for (i in 0..searchBoardRecyclerViewAdapter.getItemCount() - 1) {
                            rv_item_board_list_for_search.removeItemDecorationAt(i)
                        }
                    }

                    //데이터가 없을 때
                    if (response.body()!!.data == null) {

                        ll_home_board_board_view_for_search.visibility = View.GONE
                        ll_home_board_board_view.visibility = View.GONE

                        iv_home_board_search_fail.visibility = View.VISIBLE

                        isinSearchData = false

                    } else {
                        //데이터 있을 경우

                        BoardDataForSearch = response.body()!!.data

                        for (i in 0..BoardDataForSearch.size - 1) {

                            Log.v("asdf", "키워드 크기 = " + BoardDataForSearch[i].keywords.size)
                            BoardItemListForSearch.add(BoardItem(BoardDataForSearch[i].boardId, BoardDataForSearch[i].writerId, BoardDataForSearch[i].writer,
                                    BoardDataForSearch[i].writerImage, BoardDataForSearch[i].field, BoardDataForSearch[i].company,
                                    BoardDataForSearch[i].title, BoardDataForSearch[i].content, BoardDataForSearch[i].share, BoardDataForSearch[i].time, BoardDataForSearch[i].category, BoardDataForSearch[i].images,
                                    BoardDataForSearch[i].keywords, BoardDataForSearch[i].numOfReply, BoardDataForSearch[i].recommender, BoardDataForSearch[i].likeChk))
                        }

                        searchBoardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, BoardItemListForSearch, requestManager)
                        rv_item_board_list_for_search.adapter = searchBoardRecyclerViewAdapter
                        rv_item_board_list_for_search.layoutManager = LinearLayoutManager(ctx)

                        iv_home_board_search_fail.visibility = View.GONE
                        ll_home_board_board_view.visibility = View.GONE

                        ll_home_board_board_view_for_search.visibility = View.VISIBLE

                        isinSearchData = true


                    }

                    //여기서
                }

            }
        })
    }

    //보드 통신
    fun getBoard() {
        var getBoardResponse = networkService.getBoard(SharedPreferenceController.getAuthorization(context!!), 0, 10) // 네트워크 서비스의 getContent 함수를 받아옴
        getBoardResponse.enqueue(object : Callback<GetBoardResponse> {
            override fun onResponse(call: Call<GetBoardResponse>, response: Response<GetBoardResponse>) {
                Log.v("TAG", "보드 서버 통신 연결")

                if (response!!.isSuccessful) {
                    BoardData.clear()

                        BoardData = response.body()!!.data
                        for (i in 0..BoardData.size - 1) {

                            //Log.v("asdf","키워드 크기 = " + BoardData[i].keywords.size)
                            Log.v("asdf", "키워드 크기 = " + BoardData[i].keywords.size)
                            BoardItemList.add(BoardItem(BoardData[i].boardId, BoardData[i].writerId, BoardData[i].writer,
                                    BoardData[i].writerImage, BoardData[i].field, BoardData[i].company,
                                    BoardData[i].title, BoardData[i].content, BoardData[i].share, BoardData[i].time, BoardData[i].category, BoardData[i].images,
                                    BoardData[i].keywords, BoardData[i].numOfReply, BoardData[i].recommender, BoardData[i].likeChk))
                        }
                        Log.v("asdf", "응답 바디 = " + response.body().toString())

                        //rv_item_boardㄷ_list.removeItemDecorationAt()
                        boardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, BoardItemList, requestManager)
                        boardRecyclerViewAdapter.notifyDataSetChanged()
                        rv_item_board_list.adapter = boardRecyclerViewAdapter
                        rv_item_board_list.layoutManager = LinearLayoutManager(ctx)




                }
            }

            override fun onFailure(call: Call<GetBoardResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }


}
