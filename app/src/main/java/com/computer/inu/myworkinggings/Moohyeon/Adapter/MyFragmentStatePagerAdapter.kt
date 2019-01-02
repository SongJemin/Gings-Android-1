package com.computer.inu.myworkinggings.adapter

import android.content.Context
import android.content.Context.CONTEXT_RESTRICTED
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.computer.inu.myworkinggings.Hyunjin.Activity.TopNaviMessageNoticeActivity
import com.computer.inu.myworkinggings.Jemin.Fragment.MyPageFragment
import com.computer.inu.myworkinggings.Moohyeon.Fragment.DirectoryFragment
import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeFragment


class MyFragmentStatePagerAdapter(fm : FragmentManager, val fragmentCount : Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return HomeBoardFragment()
            1 -> return DirectoryFragment()
            2 -> return LoungeFragment()
            3 -> return TopNaviMessageNoticeActivity()
            4 -> return MyPageFragment()
            else -> return null
        }
    }

    override fun getCount(): Int = fragmentCount
}