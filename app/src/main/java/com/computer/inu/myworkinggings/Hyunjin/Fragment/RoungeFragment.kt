package com.computer.inu.myworkinggings.Hyunjin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_rounge.*
import org.jetbrains.anko.support.v4.startActivity
import com.computer.inu.myworkinggings.Hyunjin.Activity.LoungeIntroduceActivity


class RoungeFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rounge,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rl_club_photo_lounge.setOnClickListener {
            startActivity<LoungeIntroduceActivity>()
        }
    }
}