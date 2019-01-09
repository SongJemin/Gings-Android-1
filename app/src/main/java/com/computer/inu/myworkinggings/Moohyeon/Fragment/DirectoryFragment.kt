package com.computer.inu.myworkinggings.Moohyeon.Fragment

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DirectoryBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryBoardData
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_directory.*

import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.support.v4.ctx
import android.widget.Toast
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import com.computer.inu.myworkinggings.Moohyeon.Adapter.SearchingUserRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.SearchingUserData
import org.jetbrains.anko.support.v4.toast
import android.app.Activity
import android.support.v4.content.ContextCompat.getSystemService
import com.computer.inu.myworkinggings.Moohyeon.Activity.BottomNaviActivity
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Fragment.MypageIntroFragment
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryData
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectorySearchData
import com.computer.inu.myworkinggings.Moohyeon.get.GetDirectoryListResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetDirectorySearchResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import kotlinx.android.synthetic.main.fragment_directory.view.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DirectoryFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var directoryboardRecyclerViewAdapter: DirectoryBoardRecyclerViewAdapter
    lateinit var searchingUserRecyclerViewAdapter: SearchingUserRecyclerViewAdapter
    var coworkingEnabled: Int = 0
    var directorySearchData = ArrayList<DirectorySearchData>()
    var directoryData = ArrayList<DirectoryData>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_directory, container, false)
        searchingUserRecyclerViewAdapter = SearchingUserRecyclerViewAdapter(context!!, directorySearchData)
        view.rv_item_SearchingUserlist.layoutManager = GridLayoutManager(view.context, 2)
        view.rv_item_SearchingUserlist.adapter = searchingUserRecyclerViewAdapter
        view.rv_item_SearchingUserlist.setNestedScrollingEnabled(false)

        directoryboardRecyclerViewAdapter = DirectoryBoardRecyclerViewAdapter(context!!, directoryData)
        view.rv_directory_user_list.layoutManager = LinearLayoutManager(view.context)
        view.rv_directory_user_list.adapter = directoryboardRecyclerViewAdapter
        view.rv_directory_user_list.setNestedScrollingEnabled(false)
       getDirectoryListResponse()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_directory_seaching_fail.visibility = View.GONE
        rv_item_SearchingUserlist.visibility = View.GONE
        iv_directory_searching.visibility=View.GONE
        et_directory_searching.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                tv_directory_seaching_fail.visibility = View.GONE
                rv_directory_user_list.visibility = View.GONE
                tv_directory_welcome.visibility = View.GONE
                iv_directory_finish.visibility = View.VISIBLE
                iv_directory_searching.visibility = View.VISIBLE
                tv_directory_searching_text.visibility = View.VISIBLE
                tv_directory_seaching_fail.visibility = View.GONE
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rv_item_SearchingUserlist.visibility = View.GONE

            }
        })

        iv_directory_finish.visibility = View.GONE
        et_directory_searching.inputType = InputType.TYPE_CLASS_TEXT
        et_directory_searching.imeOptions = EditorInfo.IME_ACTION_SEARCH

        et_directory_searching.setOnClickListener {

            rv_directory_user_list.visibility = View.GONE
            tv_directory_welcome.visibility = View.GONE
            iv_directory_finish.visibility = View.VISIBLE

        }
        et_directory_searching.setOnEditorActionListener({ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                if (et_directory_searching.text.toString() == "") {
                    iv_directory_searching.visibility = View.GONE
                    tv_directory_searching_text.visibility = View.GONE
                    tv_directory_seaching_fail.visibility = View.VISIBLE
                } else
                    getDirectorySearchResponse()
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(et_directory_searching.getWindowToken(), 0)
            }
            handled
        })
        iv_directory_finish.setOnClickListener {
            rv_directory_user_list.visibility = View.VISIBLE
            tv_directory_welcome.visibility = View.VISIBLE
            iv_directory_finish.visibility = View.GONE
            iv_directory_searching.visibility = View.GONE
            tv_directory_searching_text.visibility = View.GONE
            rv_item_SearchingUserlist.visibility = View.GONE
            tv_directory_seaching_fail.visibility = View.GONE
            var imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(iv_directory_finish.getWindowToken(), 0)
            et_directory_searching.text.clear()
        }

    }

    fun getDirectorySearchResponse() {
        var getSearchResponse: Call<GetDirectorySearchResponse> = networkService.getDirectorySearchResponse("application/json", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", et_directory_searching.text.toString())
        getSearchResponse.enqueue(object : Callback<GetDirectorySearchResponse> {
            override fun onResponse(call: Call<GetDirectorySearchResponse>?, response: Response<GetDirectorySearchResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                        var temp: ArrayList<DirectorySearchData> = response.body()!!.data
                    if (temp != null) {
                        var position = searchingUserRecyclerViewAdapter.itemCount
                        searchingUserRecyclerViewAdapter.dataList.clear()
                        searchingUserRecyclerViewAdapter.notifyItemInserted(searchingUserRecyclerViewAdapter.itemCount)
                        searchingUserRecyclerViewAdapter.dataList.addAll(temp)
                        searchingUserRecyclerViewAdapter.notifyDataSetChanged()

                        // 여기부터
                        iv_directory_searching.visibility = View.GONE
                        tv_directory_searching_text.visibility = View.GONE
                        rv_item_SearchingUserlist.visibility = View.VISIBLE
                    } else {
                        iv_directory_searching.visibility = View.GONE
                        tv_directory_searching_text.visibility = View.GONE
                        tv_directory_seaching_fail.visibility = View.VISIBLE
                    }
                } else {
                    Log.v("TAG", "디렉토리 검색 실패")

                }
            }

            override fun onFailure(call: Call<GetDirectorySearchResponse>?, t: Throwable?) {

                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }

    fun getDirectoryListResponse() {
        var getDirectoryListResponse: Call<GetDirectoryListResponse> = networkService.getDirectoryListResponse("application/json", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",0,10)
        getDirectoryListResponse.enqueue(object : Callback<GetDirectoryListResponse> {
            override fun onResponse(call: Call<GetDirectoryListResponse>?, response: Response<GetDirectoryListResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    rv_directory_user_list.visibility = View.VISIBLE
                    directoryboardRecyclerViewAdapter.dataList.clear()
                    var temp: ArrayList<DirectoryData> = response.body()!!.data
                    if (temp != null) {
                        var position = directoryboardRecyclerViewAdapter.itemCount
                        directoryboardRecyclerViewAdapter.dataList.addAll(temp)
                        directoryboardRecyclerViewAdapter.notifyItemInserted(position)
                        iv_directory_searching.visibility = View.GONE
                        tv_directory_searching_text.visibility = View.GONE
                    }
                } else {
                    Log.v("TAG", "디렉토리 검색 실패")
                    toast("통신 실패")
                }
            }

            override fun onFailure(call: Call<GetDirectoryListResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }
}

