package com.computer.inu.myworkinggings.Moohyeon.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.computer.inu.myworkinggings.Hyunjin.Fragment.MessageFragment
import com.computer.inu.myworkinggings.Hyunjin.Fragment.NoticeFragment

class MyFragmentNoticeTabPagerAdapter(fm : FragmentManager, val fragmentCount : Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return NoticeFragment()
            1 -> return MessageFragment()
            else -> return null
        }
    }
    override fun getCount(): Int = fragmentCount
}