package com.computer.inu.myworkinggings.Seunghee.Activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.GET.GetCategoryBoardResponse
import com.computer.inu.myworkinggings.Seunghee.GET.GetCategoryLikeRankResponse
import com.computer.inu.myworkinggings.Seunghee.GET.GetCategorySearchLikeRankResponse
import com.computer.inu.myworkinggings.Seunghee.GET.GetCategorySearchResponse
import kotlinx.android.synthetic.main.activity_category_board.*
import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryBoardActivity : AppCompatActivity() {


    var isIn = false
    var isInLike = false


    //검색_추천
    lateinit var searchLikeCategoryBoardRecyclerViewAdapter: BoardRecyclerViewAdapter
    lateinit var CategoryBoardDataForSearchLike: ArrayList<BoardData>
    var CategoryBoardItemListForSearchLike = ArrayList<BoardItem>()

    //검색_최신
    lateinit var searchCategoryBoardRecyclerViewAdapter: BoardRecyclerViewAdapter
    lateinit var CategoryBoardDataForSearch: ArrayList<BoardData>
    var CategoryBoardItemListForSearch = ArrayList<BoardItem>()

    //adapter
    lateinit var LikeRankBoardRecyclerViewAdapter: BoardRecyclerViewAdapter
    lateinit var boardRecyclerViewAdapter: BoardRecyclerViewAdapter
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var requestManager: RequestManager

    //카테고리 보드
    var BoardData = ArrayList<com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData>()
    var BoardItemList = ArrayList<BoardItem>()

    //카테고리 보드_NewRank
    var LikeBoardData = ArrayList<com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData>()
    var LikeBoardItemList = ArrayList<BoardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_board)
        requestManager = Glide.with(this)

        var category_name: String = intent.getStringExtra("category_name")
        tv_category_board_category_name.setText(category_name)

        val directoryListText: Array<String> = arrayOf("질문", "영감", "협업", "샘플1", "샘플2", "샘플3", "샘플4", "샘플5", "샘플6")
        val directoryListCode: Array<String> = arrayOf("QUESTION", "INSPIRATION", "COWORKING")

        for (i in directoryListCode.indices) {
            if (directoryListText[i] == category_name)
                category_name = directoryListCode[i]
        }

        //카테고리 보드(디폴트 : 최신순)
        getCategoryBoardResponse(category_name)

        //최신순&인기순
        CategoryRankSetOnClickListener(category_name)

        /*검색*/
        //검색
        iv_btn_category_board_search.setOnClickListener {

            rl_category_board_main_bar.visibility = View.GONE
            rl_category_board_main_bar_for_search.visibility = View.VISIBLE
        }

        et_category_board_search.inputType = InputType.TYPE_CLASS_TEXT
        et_category_board_search.imeOptions = EditorInfo.IME_ACTION_SEARCH

        et_category_board_search.setOnEditorActionListener({ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {

                //검색 시
                if (et_category_board_search.text.toString() == "") {
                    et_category_board_search.visibility = View.GONE
                    iv_category_board_search_fail.visibility = View.VISIBLE

                } else {
                    toast("gkdlt")
                    //검색통신

                    toast(et_category_board_search.text.toString())

                    tv_category_board_like_rank.setOnClickListener {
                        getCategoryBoardSearchResponse(et_category_board_search.text.toString(), category_name)
                    }

                    tv_category_board_new_rank.setOnClickListener {

                        getCategorySearchLikeRankResponse(et_category_board_search.text.toString(), category_name)
                    }

                    getCategoryBoardSearchResponse(et_category_board_search.text.toString(), category_name)

                    //기존 카테고리 검색 없어지게
                    ll_category_board_new_rank_board_view.visibility = View.GONE
                }


                val imm = ctx?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(et_category_board_search.getWindowToken(), 0)
            }
            handled
        })
        onSearchClickListener()
    }

    //
    private fun onSearchClickListener() {

        /*엑스버튼 누를 경우*/
        //edittext에 있는 내용들 다 지우고, 다시 메인창 띄우기
        iv_category_board_close_btn.setOnClickListener {

            //텍스트 내용지움
            et_category_board_search.setText("")

            //상단바
            rl_category_board_main_bar_for_search.visibility = View.GONE
            rl_category_board_main_bar.visibility = View.VISIBLE

            //리사이클러뷰
            ll_category_board_search_new_rank_board_view.visibility = View.GONE
            ll_category_board_board_view.visibility = View.VISIBLE
        }
    }


    //카테고리 '검색' - 최신순 *****완벽함!!!
    private fun getCategoryBoardSearchResponse(text: String, category_code: String) {
        val getCategoryBoardsearchResponse = networkService.getCategorySearchResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                category_code, text)

        getCategoryBoardsearchResponse.enqueue(object : Callback<GetCategorySearchResponse> {


            override fun onFailure(call: Call<GetCategorySearchResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetCategorySearchResponse>, response: Response<GetCategorySearchResponse>) {
                if (response!!.isSuccessful) {

                    //그 전에 데이터가 있었을 경우, 어댑터 싹 다 지워버려!!
                    if (isIn == true) {
                        //toast("지웠어")
                        Log.v("어디들어가니", "응 ㅠㅠ?" + response.body().toString())

                        CategoryBoardDataForSearch.clear()
                        CategoryBoardItemListForSearch.clear()

                        searchCategoryBoardRecyclerViewAdapter.dataList.clear()
                        for (i in 0..searchCategoryBoardRecyclerViewAdapter.getItemCount() - 1) {
                            rv_item_board_list_for_search.removeItemDecorationAt(i)
                        }
                    }

                    //검색 데이터가 없을 경우
                    if (response.body()!!.data == null) {
                        Log.v("어디들어가니", "하 ㅠㅠ?" + response.body().toString())


                        ll_category_board_search_like_rank_board_view.visibility = View.GONE
                        ll_category_board_search_new_rank_board_view.visibility = View.GONE
                        ll_category_board_board_view.visibility = View.GONE

                        iv_category_board_search_fail.visibility = View.VISIBLE

                        isIn = false

                    } else {

                        Log.v("어디들어가니", "풱 ㅠㅠ?" + response.body().toString())


                        CategoryBoardDataForSearch = response.body()!!.data
                        Log.v("asdf", "응답 바디1 = " + response.body().toString())

                        for (i in 0..CategoryBoardDataForSearch.size - 1) {
                            Log.v("asdf", "바디 크기 = " + CategoryBoardDataForSearch.size)
                            CategoryBoardItemListForSearch.add(BoardItem(CategoryBoardDataForSearch[i].boardId, CategoryBoardDataForSearch[i].writerId, CategoryBoardDataForSearch[i].writer,
                                    CategoryBoardDataForSearch[i].writerImage, CategoryBoardDataForSearch[i].field, CategoryBoardDataForSearch[i].company,
                                    CategoryBoardDataForSearch[i].title, CategoryBoardDataForSearch[i].content, CategoryBoardDataForSearch[i].share, CategoryBoardDataForSearch[i].time, CategoryBoardDataForSearch[i].category, CategoryBoardDataForSearch[i].images,
                                    CategoryBoardDataForSearch[i].keywords, CategoryBoardDataForSearch[i].numOfReply, CategoryBoardDataForSearch[i].recommender, CategoryBoardDataForSearch[i].likeChk))
                        }

                        searchCategoryBoardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, CategoryBoardItemListForSearch, requestManager)
                        rv_item_category_board_search_new_rank_list.adapter = searchCategoryBoardRecyclerViewAdapter
                        rv_item_category_board_search_new_rank_list.layoutManager = LinearLayoutManager(ctx)

                        Log.v("asdf", "응답 바디2 = " + response.body().toString())

                        ll_category_board_board_view.visibility = View.GONE
                        ll_category_board_search_like_rank_board_view.visibility = View.GONE
                        iv_category_board_search_fail.visibility = View.GONE

                        ll_category_board_search_new_rank_board_view.visibility = View.VISIBLE

                        isIn = true
                    }
                }
            }
        })
    }

    //카테고리 '검색'_추천순
    private fun getCategorySearchLikeRankResponse(text: String, category_code: String) {
        val getCategorySearchLikerankResponse = networkService.getCategorySearchLikeRankResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                category_code,
                text
        )
        getCategorySearchLikerankResponse.enqueue(object : Callback<GetCategorySearchLikeRankResponse> {


            override fun onFailure(call: Call<GetCategorySearchLikeRankResponse>, t: Throwable) {
                Log.e("실패야?", t.toString())
            }

            override fun onResponse(call: Call<GetCategorySearchLikeRankResponse>, response: Response<GetCategorySearchLikeRankResponse>) {
                if (response!!.isSuccessful) {


                    if (isInLike == true) {
                        //toast("지웠어")

                        CategoryBoardDataForSearchLike.clear()
                        CategoryBoardItemListForSearchLike.clear()

                        searchLikeCategoryBoardRecyclerViewAdapter.dataList.clear()
                        for (i in 0..searchLikeCategoryBoardRecyclerViewAdapter.getItemCount() - 1) {
                            rv_item_board_list_for_search.removeItemDecorationAt(i)
                        }
                    }

                    if (response.body()!!.data == null) {

                        ll_category_board_board_view.visibility = View.GONE
                        ll_category_board_search_new_rank_board_view.visibility = View.GONE
                        ll_category_board_search_like_rank_board_view.visibility = View.GONE

                        iv_category_board_search_fail.visibility = View.VISIBLE

                        isInLike=false

                    } else {
                        //toast("들어와써요")

                        ll_category_board_search_like_rank_board_view.visibility = View.VISIBLE


                        CategoryBoardDataForSearchLike = response.body()!!.data

                        for (i in 0..CategoryBoardDataForSearchLike.size - 1) {
                            //Log.v("asdf","키워드 크기 = " + BoardData[i].keywords.size)
                            Log.v("asdf", "키워드 크기 = " + CategoryBoardDataForSearchLike[i].keywords.size)
                            CategoryBoardItemListForSearchLike.add(BoardItem(CategoryBoardDataForSearchLike[i].boardId, CategoryBoardDataForSearchLike[i].writerId, CategoryBoardDataForSearchLike[i].writer,
                                    CategoryBoardDataForSearchLike[i].writerImage, CategoryBoardDataForSearchLike[i].field, CategoryBoardDataForSearchLike[i].company,
                                    CategoryBoardDataForSearchLike[i].title, CategoryBoardDataForSearchLike[i].content, CategoryBoardDataForSearchLike[i].share, CategoryBoardDataForSearchLike[i].time, CategoryBoardDataForSearchLike[i].category, CategoryBoardDataForSearchLike[i].images,
                                    CategoryBoardDataForSearchLike[i].keywords, CategoryBoardDataForSearchLike[i].numOfReply, CategoryBoardDataForSearchLike[i].recommender, CategoryBoardDataForSearchLike[i].likeChk))
                        }

                        Log.v("asdf", "응답 바디 = " + response.body().toString())

                        searchLikeCategoryBoardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, CategoryBoardItemListForSearchLike, requestManager)
                        rv_item_category_board_search_like_rank_list.adapter = searchLikeCategoryBoardRecyclerViewAdapter
                        rv_item_category_board_search_like_rank_list.layoutManager = LinearLayoutManager(ctx)


                        ll_category_board_board_view.visibility = View.GONE
                        ll_category_board_search_new_rank_board_view.visibility = View.GONE
                        iv_category_board_search_fail.visibility = View.GONE

                        ll_category_board_search_like_rank_board_view.visibility = View.VISIBLE

                        isInLike=true

                    }
                }
            }
        })
    }

    //카테고리 정렬

    private fun CategoryRankSetOnClickListener(category_code: String) {

        tv_category_board_new_rank.setOnClickListener {
            getCategoryBoardResponse(category_code)
        }

        tv_category_board_like_rank.setOnClickListener {
            getCategoryLikeRankResponse(category_code)
        }

    }

    private fun getCategoryLikeRankResponse(category_code: String) {

        val getCategoryLikeRankResponse = networkService.getCategoryLikeRankResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                category_code
        )

        getCategoryLikeRankResponse.enqueue(object : Callback<GetCategoryLikeRankResponse> {

            override fun onFailure(call: Call<GetCategoryLikeRankResponse>, t: Throwable) {
                Log.e(" fail", t.toString())
            }

            override fun onResponse(call: Call<GetCategoryLikeRankResponse>, response: Response<GetCategoryLikeRankResponse>) {
                if (response.isSuccessful) {

                    LikeBoardData = response.body()!!.data

                    for (i in 0..BoardData.size - 1) {
                        Log.v("asdf", "키워드 크기 = " + LikeBoardData[i].keywords.size)
                        BoardItemList.add(BoardItem(LikeBoardData[i].boardId,
                                LikeBoardData[i].writerId, LikeBoardData[i].writer,
                                LikeBoardData[i].writerImage, LikeBoardData[i].field, LikeBoardData[i].company,
                                LikeBoardData[i].title, LikeBoardData[i].content,
                                LikeBoardData[i].share, LikeBoardData[i].time,
                                LikeBoardData[i].category, LikeBoardData[i].images,
                                LikeBoardData[i].keywords, LikeBoardData[i].numOfReply,
                                LikeBoardData[i].recommender, LikeBoardData[i].likeChk)
                        )
                    }
                    Log.v("asdf", "응답 바디 = " + response.body().toString())

                    LikeRankBoardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, BoardItemList, requestManager)
                    rv_item_category_board_new_rank_list.adapter = LikeRankBoardRecyclerViewAdapter
                    rv_item_category_board_new_rank_list.layoutManager = LinearLayoutManager(ctx)

                    //여기!!!
                    ll_category_board_new_rank_board_view.visibility = View.VISIBLE
                    ll_category_board_board_view.visibility = View.GONE

                } else {
                    ctx.toast("pleas")
                }
            }

        })


    }

    private fun getCategoryBoardResponse(category_code: String) {

        val getCategoryboardResponse = networkService.getCategoryBoardResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                category_code
        )

        getCategoryboardResponse.enqueue(object : Callback<GetCategoryBoardResponse> {

            override fun onFailure(call: Call<GetCategoryBoardResponse>, t: Throwable) {
                Log.e(" fail", t.toString())
            }

            override fun onResponse(call: Call<GetCategoryBoardResponse>, response: Response<GetCategoryBoardResponse>) {
                if (response.isSuccessful) {

                    BoardData = response.body()!!.data

                    for (i in 0..BoardData.size - 1) {
                        Log.v("asdf", "키워드 크기 = " + BoardData[i].keywords.size)
                        LikeBoardItemList.add(BoardItem(BoardData[i].boardId,
                                BoardData[i].writerId, BoardData[i].writer,
                                BoardData[i].writerImage, BoardData[i].field, BoardData[i].company,
                                BoardData[i].title, BoardData[i].content,
                                BoardData[i].share, BoardData[i].time,
                                BoardData[i].category, BoardData[i].images,
                                BoardData[i].keywords, BoardData[i].numOfReply,
                                BoardData[i].recommender, BoardData[i].likeChk)
                        )

                    }
                    Log.v("asdf", "응답 바디 = " + response.body().toString())

                    boardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, LikeBoardItemList, requestManager)
                    rv_item_category_board_list.adapter = boardRecyclerViewAdapter
                    rv_item_category_board_list.layoutManager = LinearLayoutManager(ctx)

                    //여기!!!
                    ll_category_board_new_rank_board_view.visibility = View.GONE
                    ll_category_board_board_view.visibility = View.VISIBLE

                } else {
                    ctx.toast("pleas")
                }
            }
        })
    }
}


