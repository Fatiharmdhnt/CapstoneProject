package com.capstone.herbalease.view.ingredients_detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.R
import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.data.pref.Ingredient
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.databinding.ActivityIngredientsDetailBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.adapter.BenefitAdapter
import com.capstone.herbalease.view.adapter.KeywordsAdapter
import com.capstone.herbalease.view.adapter.MenuRecommendationAdapter
import com.capstone.herbalease.view.fitur.favorite.FavoriteHistoryViewModel

class IngredientsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsDetailBinding
    private lateinit var viewModel: FavoriteHistoryViewModel
    private var isFavorited = false

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
        viewModel = ViewModelProvider(this, ViewModelFactory(this)).get(FavoriteHistoryViewModel::class.java)

        setFullScreenSize()
        setViews()
        setListeners()
        ingredient?.let { appResponseToIngredient(it) }?.let {
            Log.d("IngredientsDetailFragment", "Adding ingredient to history: $it")
            viewModel.addHistory(it)
        }
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

                // Set Kandungan
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

                // Set Khasiat
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

                // Set Keluhan
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

                // Set Recommended Menu
                it.rekomendasiMenu?.let { menuList ->
                    if (menuList.isNotEmpty()) {
                        sectionRecommendMenu.isVisible = true

                        val menuAdapter = MenuRecommendationAdapter()
                        menuAdapter.submitList(menuList)

                        rvMenuRecommendatiom.apply {
                            layoutManager =
                                LinearLayoutManager(this@IngredientsDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                            adapter = menuAdapter
                        }
                    } else {
                        sectionRecommendMenu.isVisible = false
                    }
                }
            } ?: run {
                // Handle the case when ingredient is null
            }
        }
    }

    private fun appResponseToIngredient(response: AppResponseItem): Ingredient {
        return Ingredient(
            id = response.id ?: 0,
            name = response.nama ?: "",
            imageUrl = response.imageUrl ?: "",
            description = response.deskripsi ?: "",
            listKhasiat = response.listKhasiat,
            listKeywords = response.listKeywords.map { it.keyword },
            listKandungan = response.listKandungan,
            listRekomendasi = response.rekomendasiMenu?.filterNotNull() ?: emptyList()
        )
    }

    private fun appResponseToIngredients(response: AppResponseItem?): Ingredients {
        return if (response != null) {
            Ingredients(
                id = response.id ?: 0,
                name = response.nama ?: "",
                imageUrl = response.imageUrl ?: "",
                description = response.deskripsi ?: "",
                listKhasiat = response.listKhasiat,
                listKeywords = response.listKeywords.map { it.keyword },
                listKandungan = response.listKandungan,
                listRekomendasi = response.rekomendasiMenu?.filterNotNull() ?: emptyList()
            )
        } else {
            // Provide a default value or handle the null case appropriately
            Ingredients(
                id = 0,
                name = "",
                imageUrl = "",
                description = "",
                listKhasiat = emptyList(),
                listKeywords = emptyList(),
                listKandungan = emptyList(),
                listRekomendasi = emptyList()
            )
        }
    }


    private fun setListeners() {
        binding.apply {
            toolbar.setNavigationOnClickListener { finish() }
            btnFav.setOnClickListener {
                isFavorited = !isFavorited
                if (isFavorited) {
                    btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@IngredientsDetailActivity,
                            R.drawable.ic_fav_fill
                        )
                    )
                    viewModel.addFavorite(appResponseToIngredients(ingredient))
                } else {
                    btnFav.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@IngredientsDetailActivity,
                            R.drawable.ic_fav
                        )
                    )
                    ingredient?.id?.let { it1 -> viewModel.removeFavorite(it1) }
                }
            }
        }
    }

    companion object {
        const val EXTRA_INGREDIENT = "extra_ingredient"
    }
}
