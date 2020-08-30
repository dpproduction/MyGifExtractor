package com.example.mygifextractor.ui.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.mygifextractor.R
import com.example.mygifextractor.ui.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

private val TAB_TITLES = arrayOf(
    R.string.random_tab,
    R.string.latest_tab,
    R.string.best_tab,
    R.string.hot_tab
)

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }
}