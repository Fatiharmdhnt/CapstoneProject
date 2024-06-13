package com.capstone.herbalease.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.R
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.databinding.ItemIngredientRowBinding
import com.capstone.herbalease.databinding.LoadMoreLayoutBinding


class HeadlineIngredientsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listIngredients = mutableListOf<Ingredients>()

    private lateinit var headlineViewHolder: HeadlineViewHolder
    private lateinit var buttonViewHolder: ButtonViewHolder

    var onItemClick: ((Ingredients) -> Unit)? = null
    var onLoadMoreClick: ((List<Ingredients>) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(ingredientsListData: List<Ingredients>) {
        listIngredients.clear()
        listIngredients.addAll(ingredientsListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        headlineViewHolder = HeadlineViewHolder(
            ItemIngredientRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        buttonViewHolder = ButtonViewHolder(
            LoadMoreLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        return if (viewType == INGREDIENTS) {
            headlineViewHolder
        } else {
            buttonViewHolder
        }
    }

    override fun getItemCount() = listIngredients.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (listIngredients.isNotEmpty()) {
            if (getItemViewType(position) == INGREDIENTS) {
                headlineViewHolder.bind(position)
            } else {
                buttonViewHolder.showButton()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listIngredients.size -1) {
            LOAD_MORE
        } else{
            INGREDIENTS
        }
    }

    inner class HeadlineViewHolder(private var binding: ItemIngredientRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvIngredient.text = listIngredients[position].name
                Glide.with(root)
                    .load(listIngredients[position].imageUrl)
                    .placeholder(R.drawable.dummy_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIngredient)

                root.setOnClickListener {
                    onItemClick?.invoke(listIngredients[position])
                }
            }
        }
    }

    inner class ButtonViewHolder(
        private var binding: LoadMoreLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun showButton() {
            binding.btnLoadMoreHeadline.visibility = View.VISIBLE
            binding.btnLoadMoreHeadline.setOnClickListener {
                onLoadMoreClick?.invoke(listIngredients.toList())
            }
        }
    }

    companion object {
        const val INGREDIENTS = 0
        const val LOAD_MORE = 1
    }
}