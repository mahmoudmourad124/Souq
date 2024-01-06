package com.example.souq.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.souq.adapters.HomeViewPagerAdapter
import com.example.souq.databinding.FragmentHomeBinding
import com.example.souq.fragments.categories.AccessoryFragment
import com.example.souq.fragments.categories.ChairFragment
import com.example.souq.fragments.categories.CupboardFragment
import com.example.souq.fragments.categories.FurnitureFragment
import com.example.souq.fragments.categories.MainCategoryFragment
import com.example.souq.fragments.categories.TableFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            AccessoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            FurnitureFragment(),
            TableFragment()
        )
        binding.viewPagerHome.isUserInputEnabled=false

        val viewpagerAdapter =
            HomeViewPagerAdapter(categoriesFragments, childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.viewPagerHome.adapter = viewpagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPagerHome){tab,position->
            when (position){
                0->tab.text="Main"
                1->tab.text="Accessory"
                2->tab.text="Chair"
                3->tab.text="CupBoard"
                4->tab.text="Furniture"
                5->tab.text="Table"

            }
        }.attach()
    }


}