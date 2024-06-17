package com.capstone.herbalease.view.ingredients_detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.data.model.AppResponseItem
import com.capstone.herbalease.databinding.ActivityIngredientsDetailBinding
import com.capstone.herbalease.view.adapter.BenefitAdapter
import com.capstone.herbalease.view.adapter.KeywordsAdapter

class IngredientsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsDetailBinding
    private val ingredient by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_INGREDIENT)
        } else {
            intent.getParcelableExtra(EXTRA_INGREDIENT, AppResponseItem::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFullScreenSize()
        setViews()
        setListeners()
    }

    private fun setFullScreenSize() {
        enableEdgeToEdge()
    }

    private fun setViews() {
        binding.apply {
            ingredient?.let {
                Glide.with(root)
                    .load(it.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIngredient)

                tvNameIngredients.text = it.nama
                tvDesc.text = it.deskripsi

                it.kandungan?.let { kandungan ->
                    if (kandungan.isNotEmpty()) {
                        layoutKandugnan.isVisible = true

                        val kandunganAdapter = KeywordsAdapter()
                        kandunganAdapter.submitList(kandungan.split(", "))

                        rvKandungan.apply {
                            layoutManager =
                                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                            adapter = kandunganAdapter
                        }
                    } else {
                        layoutKandugnan.isVisible = false
                    }
                }

                it.khasiat?.let { khasiat ->
                    if (khasiat.isNotEmpty()) {
                        sectionBenefit.isVisible = true

                        val benefitAdapter = BenefitAdapter()
                        benefitAdapter.submitList(khasiat.split(", "))

                        rvBenefit.apply {
                            layoutManager =
                                LinearLayoutManager(this@IngredientsDetailActivity)
                            adapter = benefitAdapter
                        }
                    } else {
                        sectionBenefit.isVisible = false
                    }
                }

                it.keyword?.let { keyword ->
                    if (keyword.isNotEmpty()) {
                        layoutKeluhan.isVisible = true

                        val keluhanAdapter = KeywordsAdapter()
                        keluhanAdapter.submitList(keyword.split(", "))

                        rvKeluhan.apply {
                            layoutManager =
                                LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                            adapter = keluhanAdapter
                        }
                    } else {
                        layoutKeluhan.isVisible = false
                    }
                }

            } ?: run {
                // Handle the case when ingredient is null
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
        }
    }

    companion object {
        const val EXTRA_INGREDIENT = "extra_ingredient"
    }
}