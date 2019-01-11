package com.computer.inu.myworkinggings.Jemin.ViewPager

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.view_pager_list_item.view.*


class MainListContentAdapter internal constructor(internal var context: Context, var myIntroImgItem : ArrayList<String>, var requestManager : RequestManager) : PagerAdapter(), View.OnClickListener {

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int  = myIntroImgItem.size

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_pager_list_item, container,false)
        Log.v("TASDF","뷰페이저 아이템 크기 = " + myIntroImgItem.size)
        Log.v("TASDF","현재 포지션 =  " + position)
        Log.v("TASDF","현재 포지션 =  " + myIntroImgItem)
        //requestManager.load(countries[position].mainListImg).into(view.view_pager_main_img)
        val drawable = context.getDrawable(R.drawable.background_rounding) as GradientDrawable

        view.view_pager_main_img.background = drawable
        view.view_pager_main_img.clipToOutline = true

        requestManager.load(myIntroImgItem[position]).fitCenter().into(view.view_pager_main_img)

        //view.isClickable = false
        //view.setOnClickListener(this)
        //view.tag = position
        container.addView(view)
        return view
    }


}