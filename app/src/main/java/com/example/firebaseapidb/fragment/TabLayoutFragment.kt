package com.example.firebaseapidb.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.firebaseapidb.R
import com.example.firebaseapidb.adapter.MainFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class TabLayoutFragment: Fragment(R.layout.tab_layout_container) {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var mainFragmentAdapter: MainFragmentAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.pager)
        tabLayout = view.findViewById(R.id.tab_layout)
       mainFragmentAdapter = MainFragmentAdapter(this)
        viewPager.requestDisallowInterceptTouchEvent(true)

        viewPager.adapter =mainFragmentAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
        if(position == 0){
    tab.text = "FoodRec"
            }else{
    tab.text = "Favorites"
        }
        }.attach()

        tabLayout.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->


        }

    }
}