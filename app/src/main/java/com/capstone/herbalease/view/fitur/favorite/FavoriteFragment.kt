package com.capstone.herbalease.view.fitur.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.capstone.herbalease.R
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.databinding.FragmentFavoriteBinding
import com.capstone.herbalease.di.FakeData
import com.capstone.herbalease.view.adapter.SearchIngredientsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SectionAdapter(this)
        binding.viewPager.adapter = sectionAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object{
//        const val EXTRA_FAVORIT = "extra_favorit"
//        const val EXTRA_HISTORY = "extra_history"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.favorite,
            R.string.history
        )
    }

}