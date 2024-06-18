package com.capstone.herbalease.view.ingredients_detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.R
import com.capstone.herbalease.data.model.AppResponseItem
import com.capstone.herbalease.databinding.ActivityIngredientsDetailBinding
import com.capstone.herbalease.view.adapter.BenefitAdapter
import com.capstone.herbalease.view.adapter.KeywordsAdapter
import com.capstone.herbalease.view.adapter.MenuRecommendationAdapter

class IngredientsDetailFragment : Fragment() {
    private var _binding: ActivityIngredientsDetailBinding? = null
    private val binding get() = _binding!!

    private var isFavorited = false

    private val ingredients by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(EXTRA_INGREDIENTS)
        } else {
            arguments?.getParcelable(
                EXTRA_INGREDIENTS,
                AppResponseItem::class.java
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityIngredientsDetailBinding.inflate(inflater, container, false)

        setViews()
        setListeners()

        return binding.root
    }

    private fun setViews() {
        binding.apply {
            ingredients?.let {
                Glide.with(root)
                    .load(it.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIngredient)

                tvNameIngredients.text = it.nama
                tvDesc.text = it.deskripsi

                // Set Kandungan
                if (it.listKandungan.isNotEmpty()) {
                    layoutKandugnan.isVisible = true

                    val kandunganAdapter = KeywordsAdapter()
                    kandunganAdapter.submitList(it.listKandungan)

                    rvKandungan.apply {
                        layoutManager =
                            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = kandunganAdapter
                    }
                } else {
                    layoutKandugnan.isVisible = false
                }

                // Set Khasiat
                if (it.listKhasiat.isNotEmpty()) {
                    sectionBenefit.isVisible = true

                    val benefitAdapter = BenefitAdapter()
                    benefitAdapter.submitList(it.listKhasiat)

                    rvBenefit.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext())
                        adapter = benefitAdapter
                    }
                } else {
                    sectionBenefit.isVisible = false
                }

                // Set Keluhan
                if (it.listKeluhan.isNotEmpty()) {
                    layoutKeluhan.isVisible = true

                    val keluhanAdapter = KeywordsAdapter()
                    keluhanAdapter.submitList(it.listKeluhan)

                    rvKeluhan.apply {
                        layoutManager =
                            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = keluhanAdapter
                    }
                } else {
                    layoutKeluhan.isVisible = false
                }

                // Set Recommended Menu
                if (it.rekomendasiMenu?.isNotEmpty() == true) {
                    sectionRecommendMenu.isVisible = true

                    val menuAdapter = MenuRecommendationAdapter()
                    menuAdapter.submitList(it.rekomendasiMenu)

                    rvMenuRecommendatiom.apply {
                        layoutManager =
                            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = menuAdapter
                    }
                } else {
                    sectionRecommendMenu.isVisible = false
                }
            } ?: run {
                // Handle the case when ingredients are null
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

            btnFav.setOnClickListener {
                isFavorited = !isFavorited
                if (isFavorited) {
                    btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fav_fill
                        )
                    )
                } else {
                    btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fav
                        )
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_INGREDIENTS = "extra_ingredients"
    }
}
