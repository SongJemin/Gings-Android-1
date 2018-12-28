package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up1.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignUp1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        cb_sign_up1_personal_check.setOnClickListener {
            if(cb_sign_up1_personal_check.isChecked()){
                cb_sign_up1_personal_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_personal_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_membership_agree_check.setOnClickListener {
            if(cb_sign_up1_membership_agree_check.isChecked()){
                cb_sign_up1_membership_agree_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_membership_agree_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_using_agree_check.setOnClickListener {
            if(cb_sign_up1_using_agree_check.isChecked()){
                cb_sign_up1_using_agree_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_using_agree_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_all_agree_check.setOnClickListener {
            if (cb_sign_up1_all_agree_check.isChecked()) {
                cb_sign_up1_personal_check.setChecked(true)
                cb_sign_up1_using_agree_check.setChecked(true)
                cb_sign_up1_membership_agree_check.setChecked(true)
            } else {
                cb_sign_up1_personal_check.setChecked(false)
                cb_sign_up1_using_agree_check.setChecked(false)
                cb_sign_up1_membership_agree_check.setChecked(false)
            }
        }

        tv_sign_up1_next_btn.setOnClickListener {
            if( cb_sign_up1_all_agree_check.isChecked()) {
                startActivity<SignUp2Activity>()
            }else{
                toast("모두 동의하셔야합니다.")
            }
        }
    }
}
