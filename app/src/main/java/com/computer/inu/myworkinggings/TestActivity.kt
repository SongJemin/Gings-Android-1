package com.computer.inu.myworkinggings

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        iv_test_test_image.visibility=View.GONE
        et_test_edit_text.setOnClickListener {
            iv_test_test_image.visibility=View.GONE
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        btn_test_picture.setOnClickListener {
            imm.hideSoftInputFromWindow(btn_test_picture.getWindowToken(), 0);
            iv_test_test_image.visibility=View.VISIBLE
        }
    }
}
