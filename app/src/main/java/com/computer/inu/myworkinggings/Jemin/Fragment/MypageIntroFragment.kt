package com.computer.inu.myworkinggings.Jemin.Fragment

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.GuestboardWriteActivity
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherGuestBoardResponse

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherIntroResponse
import com.computer.inu.myworkinggings.Jemin.ViewPager.CardPagerTransformerShift
import com.computer.inu.myworkinggings.Jemin.ViewPager.CustomViewPagerAdapter
import com.computer.inu.myworkinggings.Jemin.ViewPager.MainListContentAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.MyIntroduceData
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R

import com.computer.inu.myworkinggings.Moohyeon.get.GetGuestBoardResponse
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*

import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageIntroduceResponse
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import kotlinx.android.synthetic.main.view_pager_list_item.view.*
import org.jetbrains.anko.support.v4.ctx

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageIntroFragment : Fragment() {


    var getOtherGuestBoard = ArrayList<GuestBoardItem>()
    var guestBoardItem = ArrayList<GuestBoardItem>()
    lateinit var guestBoardAdapter: GuestBoardAdapter
    var userID : Int = 0
    var field: String = ""
    var status: String = ""
    var image: String? = ""
    var name: String = ""
    var job: String = ""
    var company: String = ""
    var coworkingEnabled: Int = 0
    var checkFlag: Int = 0
    var my_or_other_flag : Int = 0
    lateinit var getMyIntroData : MyIntroduceData

    var myIntroImgUrlList = ArrayList<String>()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var requestManager: RequestManager
    /*  var GuestBoardDataList = ArrayList<GuestBoardData>()*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragmet_my_page_introduce, container, false)
        val extra = arguments

        requestManager = Glide.with(this)
        my_or_other_flag = extra!!.getInt("my_or_other_flag")
        if(my_or_other_flag == 1){
            v.mypage_board_write_btn.visibility = View.VISIBLE
            userID = extra!!.getInt("userID")
            getOtherGuestBoard()
            getOtherIntro(v)

        }
        else{
            v.mypage_board_write_btn.visibility = View.GONE
            getMyIntro(v)
            getGuestBoardPost()
        }
        job = extra!!.getString("job")
        company = extra!!.getString("company")
        image = extra!!.getString("image")
        name = extra!!.getString("name")
        field = extra!!.getString("field")
        status = extra!!.getString("status")
        Log.v("asdf", "받는 필드 = " + field)
        Log.v("asdf", "받는 상태 = " + status)


        if(status == "-"){
            v.mypage_intro_status_layout.isSelected = false
        }
        else{
            v.mypage_intro_status_layout.isSelected = true
        }
        if(field == "-"){
            v.mypage_intro_field_layout.isSelected = false
        }
        else{
            v.mypage_intro_field_layout.isSelected = true
        }

        coworkingEnabled = extra!!.getInt("coworkingEnabled")
        if (coworkingEnabled == 1) {
            v.mypage_intro_collab_tv.text = "가능"
            v.mypage_intro_collab_layout.isSelected = true
        } else {
            v.mypage_intro_collab_tv.text = "불가능"
            v.mypage_intro_collab_layout.isSelected = false
        }

        v.mypage_board_name_tv.text = name
        v.mypage_board_company.text = job
        v.mypage_board_job.text = "/" + company
        Glide.with(ctx).load(image).into(v.mypage_board_profile_img)
        v.mypage_intro_activity_part.text =field
        // 상태 스피너
        if(status == "OPENED"){
            status = "열려있음"
        }
        else if(status == "PREPARING"){
            status = "준비중"
        }
        else if(status == "MAIN_JOB"){
            status = "본업"
        }
        else if(status == "FREELANCER"){
            status = "프리랜서"
        }
        else if(status == "RECRUITING"){
            status = "구인중"
        }
        else if(status!! == "LOOKING_JOB"){
            status = "구직중"
        }
        else if(status == "LOOKING_INVESTMENT"){
            status = "투자후원유중"
        }
        v.mypage_intro_status.text = status

        v.mypage_board_write_btn.setOnClickListener {
            val intent : Intent = Intent(activity, GuestboardWriteActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("userID", userID)
            startActivityForResult(intent,10)
        }


        return v
    }
    fun getTest(v : View){
        v.my_intro_img_viewPager.adapter = CustomViewPagerAdapter(MainListContentAdapter(context!!, myIntroImgUrlList, requestManager))
        v.my_intro_img_viewPager.setPadding(50, 0, 50, 0)
        v.my_intro_img_viewPager.pageMargin = -200
        v.my_intro_img_viewPager.offscreenPageLimit = 9

        v.my_intro_img_viewPager.clipToPadding = false
        // v.home_rc_viewPager.setPadding(40, 0, 40, 0)
        //v.home_rc_viewPager.setPageMargin(resources.displayMetrics.widthPixels / -9)

        val screen = Point()
        activity!!.windowManager.defaultDisplay.getSize(screen)

        val startOffset = 50.0f / (screen.x - 2 * 50.0f)
        v.my_intro_img_viewPager.setPageTransformer(false, CardPagerTransformerShift(v.my_intro_img_viewPager.elevation * 1.0f, v.my_intro_img_viewPager.elevation,
                0.6f, startOffset))
    }

    fun getMyIntro(v: View) {
        var getMypageIntroduceResponse = networkService.getMypageIntroduceResponse("application/json", SharedPreferenceController.getAuthorization(context!!)) // 네트워크 서비스의 getContent 함수를 받아옴
        getMypageIntroduceResponse.enqueue(object : Callback<GetMypageIntroduceResponse> {
            override fun onResponse(call: Call<GetMypageIntroduceResponse>?, response: Response<GetMypageIntroduceResponse>?) {
                Log.v("TAG", "나의 소개 페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("MyTAG", "나의 소개 페이지 서버 통신 연결 성공 = " + response.body().toString())

                    if(response.body()!!.data != null){
                        my_intro_img_viewPager.visibility = View.VISIBLE
                        mypage_board_content_tv.text = response.body()!!.data!!.content!!
                        mypage_board_datetime_tv.text = response.body()!!.data!!.time!!.substring(0, 16).replace("T", "   ")
                        myIntroImgUrlList.clear()
                        myIntroImgUrlList = response.body()!!.data!!.imgs!!
                        for(i in 0 ..myIntroImgUrlList.size-1){
                            myIntroImgUrlList.add(response.body()!!.data!!.imgs!![i])
                        }
                        //Glide.with(ctx).load(response.body()!!.data!!.imgs!![0]).into( v.my_intro_img_viewPager) // 한장만 넣을수 있음
                        v.my_intro_img_viewPager.adapter = CustomViewPagerAdapter(MainListContentAdapter(context!!, myIntroImgUrlList, requestManager))
                        v.my_intro_img_viewPager.setPadding(50, 0, 50, 0)
                        v.my_intro_img_viewPager.pageMargin = -200
                        v.my_intro_img_viewPager.offscreenPageLimit = 9

                        v.my_intro_img_viewPager.clipToPadding = false
                        // v.home_rc_viewPager.setPadding(40, 0, 40, 0)
                        //v.home_rc_viewPager.setPageMargin(resources.displayMetrics.widthPixels / -9)

                        val screen = Point()
                        activity!!.windowManager.defaultDisplay.getSize(screen)

                        val startOffset = 50.0f / (screen.x - 2 * 50.0f)
                        v.my_intro_img_viewPager.setPageTransformer(false, CardPagerTransformerShift(v.my_intro_img_viewPager.elevation * 1.0f, my_intro_img_viewPager.elevation,
                                0.6f, startOffset))
                    }
                    else{
                        v.my_intro_img_viewPager.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<GetMypageIntroduceResponse>?, t: Throwable?) {
                Log.v("MyTAG", "통신 실패 = " + t.toString())
            }
        })
    }


    fun getOtherIntro(v : View) {
        var getOtherIntroResponse = networkService.getOtherPageIntro( SharedPreferenceController.getAuthorization(context!!), userID) // 네트워크 서비스의 getContent 함수를 받아옴
        getOtherIntroResponse.enqueue(object : Callback<GetOtherIntroResponse> {
            override fun onResponse(call: Call<GetOtherIntroResponse>?, response: Response<GetOtherIntroResponse>?) {
                Log.v("TAG", "타인 소개 페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body() != null){
                        Log.v("Asdf","타인 페이지 내용 = " + response.body()!!.toString())
                        my_intro_img_viewPager.visibility = View.VISIBLE
                        if(response.body()!!.data != null){
                            mypage_board_content_tv.text = response.body()!!.data!!.content!!
                            mypage_board_datetime_tv.text = response.body()!!.data!!.time!!.substring(0, 16).replace("T", "   ")
                            myIntroImgUrlList.clear()
                            if(response.body()!!.data!!.imgs!!.size == 0){
                                v.my_intro_img_viewPager.visibility = View.GONE
                            }
                            else{
                                myIntroImgUrlList = response.body()!!.data!!.imgs!!
                                for(i in 0 ..myIntroImgUrlList.size-1){
                                    myIntroImgUrlList.add(response.body()!!.data!!.imgs!![i])
                                }

                                //Glide.with(ctx).load(response.body()!!.data!!.imgs!![0]).into( v.my_intro_img_viewPager) // 한장만 넣을수 있음
                                v.my_intro_img_viewPager.adapter = CustomViewPagerAdapter(MainListContentAdapter(context!!, myIntroImgUrlList, requestManager))
                                v.my_intro_img_viewPager.setPadding(50, 0, 50, 0)
                                v.my_intro_img_viewPager.pageMargin = -200
                                v.my_intro_img_viewPager.offscreenPageLimit = 9

                                v.my_intro_img_viewPager.clipToPadding = false
                                // v.home_rc_viewPager.setPadding(40, 0, 40, 0)
                                //v.home_rc_viewPager.setPageMargin(resources.displayMetrics.widthPixels / -9)

                                val screen = Point()
                                activity!!.windowManager.defaultDisplay.getSize(screen)

                                val startOffset = 50.0f / (screen.x - 2 * 50.0f)
                                v.my_intro_img_viewPager.setPageTransformer(false, CardPagerTransformerShift(v.my_intro_img_viewPager.elevation * 1.0f, my_intro_img_viewPager.elevation,
                                        0.6f, startOffset))
                            }

                        }
                        else{
                            v.my_intro_img_viewPager.visibility = View.GONE
                        }

                    }


                }
            }

            override fun onFailure(call: Call<GetOtherIntroResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }



    fun getGuestBoardPost() {
        var getGuestResponse: Call<GetGuestBoardResponse> = networkService.getGuestBoardResponse("application/json",  SharedPreferenceController.getAuthorization(context!!))
        getGuestResponse.enqueue(object : Callback<GetGuestBoardResponse> {
            override fun onResponse(call: Call<GetGuestBoardResponse>?, response: Response<GetGuestBoardResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    guestBoardAdapter = GuestBoardAdapter(context!!, guestBoardItem)
                    guestBoardAdapter.guestBoardItems.clear()
                    val temp: ArrayList<GuestBoardItem> = response.body()!!.data

                    if (temp.size > 0) {
                        val position = guestBoardAdapter.itemCount
                        guestBoardAdapter.guestBoardItems.addAll(temp)
                        guestBoardAdapter.notifyItemInserted(position)

                        guestBoardAdapter = GuestBoardAdapter(context!!, guestBoardItem)
                        mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(context)
                        mypage_guestboard_recyclerview.adapter = guestBoardAdapter
                        mypage_guestboard_recyclerview.setNestedScrollingEnabled(false)
                    }


                }
            }

            override fun onFailure(call: Call<GetGuestBoardResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })

    }

    fun getOtherGuestBoard(){
        var getOtherGuestBoardResponse: Call<GetOtherGuestBoardResponse>  = networkService.getOtherGuestBoard( SharedPreferenceController.getAuthorization(context!!),userID)
        getOtherGuestBoardResponse.enqueue(object : Callback<GetOtherGuestBoardResponse> {
            override fun onResponse(call: Call<GetOtherGuestBoardResponse>?, response: Response<GetOtherGuestBoardResponse>?) {
                Log.v("TAG", "타인 게스트 보드 조회 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "타인 게스트 보드 조회 서버 통신 조회 연결 = " + response.body().toString())
                    getOtherGuestBoard.clear()
                    getOtherGuestBoard = response.body()!!.data

                    guestBoardAdapter = GuestBoardAdapter(activity!!, getOtherGuestBoard)
                    mypage_guestboard_recyclerview.adapter = guestBoardAdapter
                    mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(activity)
                    mypage_guestboard_recyclerview.setNestedScrollingEnabled(false)

                }
            }

            override fun onFailure(call: Call<GetOtherGuestBoardResponse>?, t: Throwable?) {
                Log.v("TAG", "타인 게스트 보드 조회 서버 통신 실패 = " +t.toString())
            }
        })

    }

}