package com.computer.inu.myworkinggings.Jemin.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_password_confirm.*
import kotlinx.android.synthetic.main.dialog_password_confirm.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PasswordConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_confirm)

        // 테스트 연결
        password_confirm_confirm_btn.setOnClickListener {
            val intent = Intent(applicationContext, UnsubscribeActivity::class.java)
            startActivity(intent)

        }
        setOnBtnClickListener()

    }

    //비밀번호 변경확인에 대한 알림창
    private fun setOnBtnClickListener(){
        password_confirm_confirm_btn.setOnClickListener {
            showDialog()
        }
    }

    protected fun showDialog() {
        var showDialog = Dialog(this)
        showDialog.setCancelable(true)

        val preferMemberDialogView = this!!.layoutInflater.inflate(R.layout.dialog_password_confirm, null)
        showDialog.setContentView(preferMemberDialogView)
        showDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        try {

        } catch (e: Exception) {
        }
        showDialog.show()
    }
}
