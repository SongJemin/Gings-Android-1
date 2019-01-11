package com.computer.inu.myworkinggings.Jemin.Activity

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.widget.Toast
import com.computer.inu.myworkinggings.Hyunjin.Activity.TopNaviMessageNoticeActivity
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeFragment
import com.computer.inu.myworkinggings.Jemin.Fragment.MyPageFragment
import com.computer.inu.myworkinggings.Moohyeon.Fragment.DirectoryFragment
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_exit.*
import kotlinx.android.synthetic.main.dialog_exit.view.*
import android.app.Activity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "MainActivity"

    val FINISH_INTERVAL_TIME = 2000
    var backPressedTime : Long = 0
    lateinit var boardRecyclerViewAdapter: BoardRecyclerViewAdapter

    private val FRAGMENT1 = 1
    private val FRAGMENT2 = 2
    private val FRAGMENT3 = 3
    private val FRAGMENT4 = 4
    private val FRAGMENT5 = 5
    private val MY_PERMISSIONS_REQUEST = 100
    private val MY_PERMISSIONS_REQUEST2 = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 탭 버튼에 대한 리스너 연결
        main_hometab_btn!!.setOnClickListener(this)
        main_directory_btn!!.setOnClickListener(this)
        main_lounge_btn!!.setOnClickListener(this)
        main_alarm_btn!!.setOnClickListener(this)
        main_mypage_btn!!.setOnClickListener(this)

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST)
        }
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST2)
        }



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
                // '라운지 탭' 클릭 시 '라운지 프래그먼트' 호출
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


    public fun callFragment(frament_no: Int) {

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

    // 백버튼 클릭 시
    override fun onBackPressed() {
        showExitDialog()
    }

    protected fun showExitDialog() {
        var exitDialog = Dialog(this)
        exitDialog.setCancelable(true)
        exitDialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        val exitDialogView = this!!.layoutInflater.inflate(R.layout.dialog_exit, null)
        exitDialog.setContentView(exitDialogView)

        exitDialog.exit_dialog_cancel_tv.setOnClickListener {
            exitDialog.dismiss()
        }
        exitDialog.exit_dialog_exit_tv.setOnClickListener {
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
        exitDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.v(TAG, "메인 액티비티 리턴")
        if (requestCode == 20) {
            // 보드 상세에서 이름이나 프로필사진 선택으로 넘어올 경우
            if (data!!.getIntExtra("userID", 0) != 0) {
                Log.v(TAG, "리턴 번호 값 = " + data!!.getIntExtra("boardID", 0))
                Log.v(TAG, "리턴 유저 값 = " + data!!.getIntExtra("userID", 0))
                main_mypage_btn.setSelected(true)
                main_directory_btn.setSelected(false)
                main_lounge_btn.setSelected(false)
                main_alarm_btn.setSelected(false)
                main_hometab_btn.setSelected(false)
                callFragment(FRAGMENT5)
            }
            // 보드 상세에서 백버튼으로 넘어올 경우
            else {

            }

        }
    }
}