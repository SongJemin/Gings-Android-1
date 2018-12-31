package com.computer.inu.myworkinggings.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.computer.inu.myworkinggings.Hyunjin.Activity.TopNaviMessageNoticeActivity
import com.computer.inu.myworkinggings.Hyunjin.Fragment.RoungeFragment
import com.computer.inu.myworkinggings.Jemin.Fragment.MyPageFragment
import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment

import com.computer.inu.myworkinggings.fragment.*

class MyFragmentStatePagerAdapter(fm : FragmentManager, val fragmentCount : Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return HomeBoardFragment()
            1 -> return DirectoryFragment()
            2 -> return RoungeFragment()
            3 -> return TopNaviMessageNoticeActivity()
            4 -> return MyPageFragment()
            else -> return null
        }
    }

    override fun getCount(): Int = fragmentCount
}