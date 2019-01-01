package com.computer.inu.myworkinggings.Seunghee.Activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_category_menu.*
import org.jetbrains.anko.startActivity

class CategoryMenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_menu)

        val directoryList : Array<RelativeLayout> = arrayOf(rl_btn_category_menu_question,
                rl_btn_category_menu_inspiration,
                rl_btn_category_menu_collaboration,
                rl_btn_category_menu_sample1,
                rl_btn_category_menu_sample2,
                rl_btn_category_menu_sample3,
                rl_btn_category_menu_sample4,
                rl_btn_category_menu_sample5,
                rl_btn_category_menu_sample6
                )
        val directoryListText : Array<String> = arrayOf("질문","영감","협업","샘플1","샘플2","샘플3","샘플4","샘플5","샘플6")


        for(i in directoryList.indices)
        {
            directoryList[i].setOnClickListener{
                startActivity<CategoryBoardActivity>("category_name" to directoryListText[i])
            }
        }
    }

}
