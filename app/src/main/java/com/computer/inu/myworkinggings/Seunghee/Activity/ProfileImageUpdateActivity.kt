package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.AlbumRecyclerViewAdapter
import com.sopt.gings.data.AlbumData
import kotlinx.android.synthetic.main.activity_profile_image_update.*

class ProfileImageUpdateActivity : AppCompatActivity() {

    lateinit var profileAlbumRecyclerViewAdapter: AlbumRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_image_update)

        setRecyclerView()

    }

    private fun setRecyclerView(){

        //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입

        //임시데이터set
        var dataList: ArrayList<AlbumData> = ArrayList()
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))


        profileAlbumRecyclerViewAdapter = AlbumRecyclerViewAdapter(this, dataList)
        rv_upboard_profile_album_list.adapter = profileAlbumRecyclerViewAdapter
        rv_upboard_profile_album_list.layoutManager = GridLayoutManager(this,3)

    }
}
