package com.example.firebaseapidb.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebaseapidb.fragment.HomeFragment
import com.example.firebaseapidb.fragment.HomeFragmentSecond

class MainFragmentAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1-> HomeFragmentSecond()
            else-> HomeFragment()
        }
    }

}