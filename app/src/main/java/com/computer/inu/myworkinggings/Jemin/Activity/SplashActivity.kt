package com.computer.inu.myworkinggings.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.R
import android.os.Looper.loop
import android.view.View
import com.airbnb.lottie.LottieAnimationView



class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
/*
        val animationView = findViewById<View>(R.id.animation_view) as LottieAnimationView
        animationView.setAnimation("gings_splash.json")
        animationView.loop(true)
        animationView.playAnimation()
*/
        var intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        //val hd = Handler()
        //hd.postDelayed(splashhandler(), 1000) // 3000ms=3초후에 핸들러 실행 //딜레이 3000
    }

    private inner class splashhandler : Runnable {
        override fun run() {
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
          //  overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // fade in, fade out 애니메이션 효과
           // this@SplashActivity.finish() // 스플래쉬 페이지 액티비티 스택에서 제거
        }
    }

    override fun onBackPressed() {
        //스플래쉬 화면에서 뒤로가기 버튼 금지
    }


}