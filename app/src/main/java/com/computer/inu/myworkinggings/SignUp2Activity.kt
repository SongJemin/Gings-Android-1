package com.computer.inu.myworkinggings

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignUp2Activity : AppCompatActivity() {
    var pw: String = String()
    var repw: String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        tv_sign_up2_confirm_number_send_message.setOnClickListener {

            imm.hideSoftInputFromWindow(tv_sign_up2_confirm_number_send_message.getWindowToken(), 0);
            pw = et_sign_up2_pw.text.toString()
            repw = et_sign_up2_re_pw.text.toString()
            if (pw.equals(repw)) {

                startActivity<SignUp3Activity>()
            } else {
                toast("비밀번호 오류")
            }
        }
    }
}
