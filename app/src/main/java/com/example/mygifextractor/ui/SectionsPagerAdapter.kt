package com.example.mygifextractor.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val NUM_PAGES = 4

class SectionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return GifFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }
}
