package com.capstone.herbalease.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.databinding.ItemIngredientSearchRowBinding

class FavHisAdapter : RecyclerView.Adapter<FavHisAdapter.ViewHolder>() {

    private val list = ArrayList<AppResponseItem>()
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(data : AppResponseItem)
    }

    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun submitList(data : List<AppResponseItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: ItemIngredientSearchRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(list: AppResponseItem, onItemClickListener: OnItemClickListener?){
            binding.root.setOnClickListener{
                onItemClickListener?.onItemClick(list)
            }

            Glide.with(itemView)
                .load(list.imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivIngredient)

            binding.tvTitle.text = list.nama
            binding.tvDesc.text = list.deskripsi
            val keywordsAdapter = KeywordsAdapter()
            val keywordList = list.keyword?.split(", ")
            keywordsAdapter.submitList(keywordList)
            keywordsAdapter.setListKeyword(list.listKeywords)

            binding.rvKeywords.apply {
                layoutManager =
                    LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = keywordsAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHisAdapter.ViewHolder {
        val binding = ItemIngredientSearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavHisAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}