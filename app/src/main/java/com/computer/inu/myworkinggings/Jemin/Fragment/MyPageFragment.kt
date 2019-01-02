package com.computer.inu.myworkinggings.Jemin.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Activity.PasswdModifyActivity
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Hyunjin.ProfileSettingMenuActivity
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import org.jetbrains.anko.support.v4.startActivity


class MyPageFragment : Fragment() {


    // 처음 프래그먼트 추가
    fun addFragment(fragment : Fragment){
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.mypage_content_layout, fragment)
        transaction.commit()
    }

    // 프래그먼트 교체
    fun replaceFragment(fragment: Fragment)
    {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.mypage_content_layout, fragment)
        transaction.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_my_page,container,false)
        v.mypage_act_view.visibility = View.INVISIBLE
        addFragment(MypageIntroFragment())
        v.mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
        v.mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))

        // '소개' 클릭 시
        v.mypage_intro_btn.setOnClickListener {
            Log.v("asdf","소개 클릭")
            mypage_act_btn.isSelected = false
            mypage_intro_btn.isSelected = true
            mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
            mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))
            mypage_intro_view.setVisibility(View.VISIBLE)
            mypage_act_view.setVisibility(View.INVISIBLE)
            replaceFragment(MypageIntroFragment())
        }

        // '활동' 클릭 시
        v.mypage_act_btn.setOnClickListener {
            Log.v("asdf","활동 클릭")
            mypage_act_btn.isSelected = true
            mypage_intro_btn.isSelected = false
            mypage_intro_btn.setTextColor(Color.parseColor("#bcc5d3"))
            mypage_act_btn.setTextColor(Color.parseColor("#b0caea"))
            mypage_act_view.setVisibility(View.VISIBLE)
            mypage_intro_view.setVisibility(View.INVISIBLE)
            replaceFragment(MypageActFragment())
        }

        v.iv_btn_my_page_setting.setOnClickListener {
            startActivity<ProfileSettingMenuActivity>()
        }

        // 테스트 연결
        v.mypage_background_img.setOnClickListener {
            var intent = Intent(activity, PasswdModifyActivity::class.java)
            startActivity(intent)
        }

        return v
    }
}