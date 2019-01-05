package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.computer.inu.myworkinggings.Hyunjin.Get.GetSearchClub
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.adapter.MyFragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_bottom_navi.*
import kotlinx.android.synthetic.main.bottom_navigation_tab.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomNaviActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navi)

        configureBottomNavigation()

    }
    private fun configureBottomNavigation(){
        vp_bottom_navi_act_frag_pager.adapter = MyFragmentStatePagerAdapter(supportFragmentManager, 5)
        //vp_bottom_navi_act_frag_pager.offscreenPageLimit = 5
        // ViewPager와 Tablayout을 엮어줍니다!
        tl_bottom_navi_act_bottom_menu.setupWithViewPager(vp_bottom_navi_act_frag_pager)
        //TabLayout에 붙일 layout을 찾아준 다음
        val bottomNaviLayout : View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)

        //탭 하나하나 TabLayout에 연결시켜줍니다.
        tl_bottom_navi_act_bottom_menu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_home_board) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_directory) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_rounge) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_notice) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(4)!!.customView = bottomNaviLayout.findViewById(R.id.btn_bottom_navi_my_page) as RelativeLayout
    }


}
