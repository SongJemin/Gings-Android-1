package com.computer.inu.myworkinggings.Seunghee.Activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_home_board_more_btn_mine.*
import org.jetbrains.anko.startActivity

class HomeBoardMoreBtnMineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board_more_btn_mine)


        rl_btn_home_board_more_btn_mine_modify.setOnClickListener {

            val modifyBoardID : Int = intent.getIntExtra("BoardId", 0).toInt()
            startActivity<UpBoardActivity>("ModifyBoardID" to modifyBoardID)
        }
    }

}
