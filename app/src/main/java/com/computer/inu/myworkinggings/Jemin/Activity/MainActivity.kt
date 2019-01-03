package com.computer.inu.myworkinggings.Jemin.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton

import android.util.Log
import com.computer.inu.myworkinggings.Hyunjin.Activity.TopNaviMessageNoticeActivity
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeFragment
import com.computer.inu.myworkinggings.Jemin.Fragment.MyPageFragment
import com.computer.inu.myworkinggings.Moohyeon.Fragment.DirectoryFragment
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private val FRAGMENT1 = 1
    private val FRAGMENT2 = 2
    private val FRAGMENT3 = 3
    private val FRAGMENT4 = 4
    private val FRAGMENT5 = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 탭 버튼에 대한 리스너 연결
        main_hometab_btn!!.setOnClickListener(this)
        main_directory_btn!!.setOnClickListener(this)
        main_lounge_btn!!.setOnClickListener(this)
        main_alarm_btn!!.setOnClickListener(this)
        main_mypage_btn!!.setOnClickListener(this)

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAGMENT1)

        main_hometab_btn.setSelected(true)

    }

    // 버튼 클릭 시
    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_hometab_btn -> {
                main_hometab_btn.setSelected(true)
                main_directory_btn.setSelected(false)
                main_lounge_btn.setSelected(false)
                main_alarm_btn.setSelected(false)
                main_mypage_btn.setSelected(false)
                // '홈보드 탭' 클릭 시 '홈 보드 프래그먼트' 호출
                callFragment(FRAGMENT1)
            }

            R.id.main_directory_btn ->{
                main_directory_btn.setSelected(true)
                main_hometab_btn.setSelected(false)
                main_lounge_btn.setSelected(false)
                main_alarm_btn.setSelected(false)
                main_mypage_btn.setSelected(false)
                // '디렉토리 탭' 클릭 시 '디렉토리 프래그먼트' 호출
                callFragment(FRAGMENT2)
            }

            R.id.main_lounge_btn -> {
                main_lounge_btn.setSelected(true)
                main_directory_btn.setSelected(false)
                main_hometab_btn.setSelected(false)
                main_alarm_btn.setSelected(false)
                main_mypage_btn.setSelected(false)
                // '추천 탭' 클릭 시 '추천 프래그먼트' 호출
                callFragment(FRAGMENT3)
            }

            R.id.main_alarm_btn ->{
                main_alarm_btn.setSelected(true)
                main_directory_btn.setSelected(false)
                main_lounge_btn.setSelected(false)
                main_hometab_btn.setSelected(false)
                main_mypage_btn.setSelected(false)
                // '알림 탭' 클릭 시 '알림 프래그먼트' 호출
                callFragment(FRAGMENT4)
            }
            R.id.main_mypage_btn ->{
                main_mypage_btn.setSelected(true)
                main_directory_btn.setSelected(false)
                main_lounge_btn.setSelected(false)
                main_alarm_btn.setSelected(false)
                main_hometab_btn.setSelected(false)
                // '마이페이지 탭' 클릭 시 '마이페이지 프래그먼트' 호출
                callFragment(FRAGMENT5)
            }
        }
    }


    private fun callFragment(frament_no: Int) {

        // 프래그먼트 사용을 위해
        val transaction = supportFragmentManager.beginTransaction()
        when (frament_no) {
            1 -> {
                // '홈보드 탭' 호출
                val mainFragment = HomeBoardFragment()
                transaction.replace(R.id.main_fragment_container, mainFragment)
                transaction.commit()
            }

            2 -> {
                // '디렉토리 탭' 호출
                val directoryFragment = DirectoryFragment()
                transaction.replace(R.id.main_fragment_container, directoryFragment)
                transaction.commit()
            }

            3 -> {
                // '라운지 탭' 호출
                val loungeFragment = LoungeFragment()
                transaction.replace(R.id.main_fragment_container, loungeFragment)
                transaction.commit()
            }

            4 -> {
                // '알림 탭' 호출
                val alarmFragment = TopNaviMessageNoticeActivity()
                transaction.replace(R.id.main_fragment_container, alarmFragment)
                transaction.commit()
            }
            5 -> {
                // '마이페이지 탭' 호출
                val mypageFragment = MyPageFragment()
                transaction.replace(R.id.main_fragment_container, mypageFragment)
                transaction.commit()
            }
        }
    }
}