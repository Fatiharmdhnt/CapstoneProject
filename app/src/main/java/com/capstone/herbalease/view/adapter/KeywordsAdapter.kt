package com.capstone.herbalease.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.herbalease.data.model.response.Keyword
import com.capstone.herbalease.databinding.ItemKeywordLayoutBinding


class KeywordsAdapter :
    ListAdapter<String, KeywordsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemKeywordLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    inner class MyViewHolder(private val binding: ItemKeywordLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String, position: Int) {
            binding.tvKeyword.text = if (position != 0) ", $keyword" else " $keyword"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val listKeyword = ArrayList<Keyword>()

    fun setListKeyword(key : List<Keyword>){
        key.forEach {
            listKeyword.add(it)
        }
    }

    override fun getItemCount(): Int = listKeyword.size

}