package com.computer.inu.myworkinggings.Hyunjin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeEventFragment
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_lounge_introduce.*
import org.jetbrains.anko.startActivity


class LoungeIntroduceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lounge_introduce)

        addFragment(LoungeEventFragment())

        tv_lounge_introduce.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility=View.VISIBLE
            ll_lounge_introduce_introduce_schedule.visibility=View.GONE
        }

        tv_lounge_calendar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility= View.GONE
            ll_lounge_introduce_introduce_schedule.visibility=View.VISIBLE
        }


    }

    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_event_image, fragment)
        transaction.commit()
    }

}
