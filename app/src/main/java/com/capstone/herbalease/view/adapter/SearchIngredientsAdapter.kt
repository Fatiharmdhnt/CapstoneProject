package com.capstone.herbalease.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.databinding.ItemIngredientSearchRowBinding

class SearchIngredientsAdapter :
    ListAdapter<Ingredients, SearchIngredientsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    var onIngredientsClick: ((Ingredients) -> Unit)? = null
    var isFromSearch: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemIngredientSearchRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class MyViewHolder(private val binding: ItemIngredientSearchRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: Ingredients) {
            with(binding) {
                Glide.with(root)
                    .load(ingredients.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIngredient)

                tvTitle.text = ingredients.name
                tvDesc.text = ingredients.description

                if (isFromSearch) {
                    if (ingredients.listKeluhan.isNotEmpty()) {
                        layoutKeywords.isVisible = true

                        val keywordsAdapter = KeywordsAdapter()
                        keywordsAdapter.submitList(ingredients.listKeluhan)

                        rvKeywords.apply {
                            layoutManager =
                                LinearLayoutManager(
                                    root.context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            adapter = keywordsAdapter
                        }
                    }
                }

                root.setOnClickListener {
                    onIngredientsClick?.invoke(ingredients)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredients>() {
            override fun areItemsTheSame(
                oldItem: Ingredients,
                newItem: Ingredients
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Ingredients,
                newItem: Ingredients
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}