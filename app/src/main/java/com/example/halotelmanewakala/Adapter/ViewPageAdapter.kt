package com.example.halotelmanewakala.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.halotelmanewakala.Fragments.*

class ViewPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }


    //
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Wakala()
            }
            1 -> {
                WakalaMkuu()
            }
            2 -> {
                FloatOut()
            }
            3 -> {
                FloatIn()
            }
            4 -> {
                Balance()
            }
            else -> {
                throw IllegalArgumentException()
            }

        }
    }
}