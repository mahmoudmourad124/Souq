package com.example.souq.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    private val fragmentList: List<Fragment>,
    fm:FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm,lifecycle){
    //fragmentManger,lifeCycle
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}