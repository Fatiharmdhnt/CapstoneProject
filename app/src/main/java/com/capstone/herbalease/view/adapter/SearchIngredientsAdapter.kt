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
import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.databinding.ItemIngredientSearchRowBinding

class SearchIngredientsAdapter :
    ListAdapter<AppResponseItem, SearchIngredientsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    var onIngredientsClick: ((AppResponseItem) -> Unit)? = null
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
        fun bind(ingredient: AppResponseItem) {
            with(binding) {
                Glide.with(root)
                    .load(ingredient.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIngredient)

                tvTitle.text = ingredient.nama
                tvDesc.text = ingredient.deskripsi

                if (isFromSearch) {
                    if (!ingredient.keyword.isNullOrEmpty()) {
                        layoutKeywords.isVisible = true

                        val keywordsAdapter = KeywordsAdapter()
                        keywordsAdapter.submitList(ingredient.keyword!!.split(", "))

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
                    onIngredientsClick?.invoke(ingredient)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AppResponseItem>() {
            override fun areItemsTheSame(
                oldItem: AppResponseItem,
                newItem: AppResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AppResponseItem,
                newItem: AppResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}