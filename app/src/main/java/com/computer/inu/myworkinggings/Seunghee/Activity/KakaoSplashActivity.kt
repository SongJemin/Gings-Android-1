package com.computer.inu.myworkinggings.Seunghee.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.computer.inu.myworkinggings.Moohyeon.Activity.DetailBoardActivity
import com.computer.inu.myworkinggings.R
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class KakaoSplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kakao_splash)

        var kakaoBoardId : Uri? = null
        kakaoBoardId = intent.data

        if (kakaoBoardId != null) {

            if(kakaoBoardId.getQueryParameter("boardIDValue") == null){
                Log.v("asdf","룸아이디값 못받아옴")
                val hd = Handler()
                hd.postDelayed(splashhandler(), 3000) // 3000ms=3초후에 핸들러 실행 //딜레이 3000
            }
            else{

                Log.v("asdf","룸아이디값 받아옴")
                var boardIDValue = kakaoBoardId.getQueryParameter("boardIDValue")
                var boardID = Integer.parseInt(boardIDValue)

                Log.v("카카오ㄷ톡쓰,,,",boardID.toString())
                startActivity<DetailBoardActivity>("BoardId" to boardID)



                val hd = Handler()
                hd.postDelayed(splashhandler(), 3000) // 3000ms=3초후에 핸들러 실행 //딜레이 3000
            }
        }
    }

    private inner class splashhandler : Runnable {
        override fun run() {
            /*
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)*/



            //toast()
            //startActivity<DetailBoardActivity>("boardId",)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // fade in, fade out 애니메이션 효과
            this@KakaoSplashActivity.finish() // 스플래쉬 페이지 액티비티 스택에서 제거
        }
    }

    override fun onBackPressed() {
        //스플래쉬 화면에서 뒤로가기 버튼 금지
    }


}