package com.computer.inu.myworkinggings.Hyunjin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.myworkinggings.Jemin.Activity.MypageUpdateActivity
import com.computer.inu.myworkinggings.Jemin.Activity.PasswdModifyActivity
import com.computer.inu.myworkinggings.Jemin.Activity.UnsubscribeActivity
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.ProfileImageUpdateActivity
import com.computer.inu.myworkinggings.Seunghee.Activity.ProfileInfoUpdateActivity
import kotlinx.android.synthetic.main.activity_profile_setting_menu.*
import org.jetbrains.anko.startActivity

class ProfileSettingMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting_menu)


        /*프로필 정보*/
        //프로필 사진 수정
        rl_btn_profile_setting_menu_image_modify.setOnClickListener {
            startActivity<ProfileImageUpdateActivity>()
        }

        //프로필 정보 수정
        rl_btn_profile_setting_menu_profile_info_update.setOnClickListener{
            startActivity<ProfileInfoUpdateActivity>()
        }
        //자기소개 수정
        rl_btn_profile_setting_menu_mypage_update.setOnClickListener {
            startActivity<MypageUpdateActivity>()
        }


        /*보안*/
        //비밀번호 변경
        rl_btn_profile_setting_menu_password_modify.setOnClickListener {
            startActivity<PasswdModifyActivity>()
        }
        //로그아웃

        //탈퇴하기
        rl_btn_profile_setting_menu_unsubscrible.setOnClickListener{
            startActivity<UnsubscribeActivity>()
        }


        /**/
        //종료버튼
        iv_btn_profile_setting_close.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in)

        }
    }
}
