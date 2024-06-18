package com.capstone.herbalease.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.herbalease.data.model.RekomendasiMenu
import com.capstone.herbalease.databinding.ItemMenuRecommendationBinding

class MenuRecommendationAdapter : RecyclerView.Adapter<MenuRecommendationAdapter.MenuViewHolder>() {

    private val items = mutableListOf<RekomendasiMenu>()

    fun submitList(list: List<RekomendasiMenu?>) {
        items.clear()
        items.addAll(list.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class MenuViewHolder(private val binding: ItemMenuRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RekomendasiMenu) {
            binding.apply {
                tvMenu.text = item.name
                Glide.with(root.context)
                    .load(item.url)
                    .into(ivMenu)
            }
        }
    }
}
