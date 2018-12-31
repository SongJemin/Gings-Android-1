package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.computer.inu.myworkinggings.Hyunjin.Activity.BottomNavigationMessageSendTabActivity
import com.computer.inu.myworkinggings.Hyunjin.Activity.TopNaviMessageNoticeSend1Activity
import com.computer.inu.myworkinggings.Hyunjin.Fragment.MessageSend1Fragment

class MessageSend2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_send2)

        addFragment(MessageSend1Fragment())
        addFragment_sub(TopNaviMessageNoticeSend1Activity())
        addFragment_sub2(BottomNavigationMessageSendTabActivity())
    }

    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_top_messagesend_fragment_block, fragment)
        transaction.commit()
    }

    private fun addFragment_sub(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_top_messagesend_tab, fragment)
        transaction.commit()
    }

    private fun addFragment_sub2(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_bottom_messagesend_tab, fragment)
        transaction.commit()
    }
}
