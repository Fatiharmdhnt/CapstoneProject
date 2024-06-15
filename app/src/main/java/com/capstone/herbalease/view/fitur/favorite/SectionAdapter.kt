package com.capstone.herbalease.view.fitur.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionAdapter (activity : Fragment) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteListFragment()
            1 -> fragment = HistoryListFragment()
        }
        return fragment as Fragment
    }
}