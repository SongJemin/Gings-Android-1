package com.computer.inu.myworkinggings.Hyunjin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeEventFragment
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_lounge_introduce.*


class LoungeIntroduceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lounge_introduce)

        addFragment(LoungeEventFragment())

        rl_lounge_introuce_introudeuce_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility=View.VISIBLE
            ll_lounge_introduce_introduce_schedule.visibility=View.GONE
        }

        rl_lounge_introuce_event_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility= View.GONE
            ll_lounge_introduce_introduce_schedule.visibility=View.VISIBLE
        }

        btn_lounge_back.setOnClickListener{
            finish()
            //replaceFragment(LoungeFragment())
        }


    }

    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_event_image, fragment)
        transaction.commit()
    }

}
