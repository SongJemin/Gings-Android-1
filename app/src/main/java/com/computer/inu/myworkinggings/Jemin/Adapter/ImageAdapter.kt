package com.computer.inu.myworkinggings.Jemin.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.R

class ImageAdapter internal constructor(internal var context: Context, var requestManager: RequestManager, var data: ArrayList<String>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.item_homeboard_img_more, null)
        val image_container = v.findViewById(R.id.image_container) as ImageView

//        Glide.with(context).load(data[position]).override(360,200).into(image_container)
        Glide.with(context).load(data[position]).centerCrop().into(image_container)
        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }
}