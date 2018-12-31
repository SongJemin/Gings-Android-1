package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.computer.inu.myworkinggings.Moohyeon.Adapter.MyFragmentNoticeTabPagerAdapter
import kotlinx.android.synthetic.main.activity_top_navi_message_notice.*

class TopNaviMessageNoticeActivity : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureTopNavigation()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_top_navi_message_notice,container,false)

    }
    private fun configureTopNavigation(){
        vp_top_navi_act_frag_pager.adapter = MyFragmentNoticeTabPagerAdapter(childFragmentManager,2)
// ViewPager와 Tablayout을 엮어줍니다!
        tl_top_navi_act_top_menu.setupWithViewPager(vp_top_navi_act_frag_pager)
//TabLayout에 붙일 layout을 찾아준 다음
        val topNaviLayout : View = this.layoutInflater.inflate(R.layout.top_navigation_tab_message_and_notice, null, false)
//탭 하나하나 TabLayout에 연결시켜줍니다.
        tl_top_navi_act_top_menu.getTabAt(0)!!.customView = topNaviLayout.findViewById(R.id.btn_top_navi_notice) as RelativeLayout
        tl_top_navi_act_top_menu.getTabAt(1)!!.customView = topNaviLayout.findViewById(R.id.btn_top_message) as RelativeLayout
    }
}