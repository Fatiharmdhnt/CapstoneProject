package com.capstone.herbalease.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.herbalease.data.model.Keyword
import com.capstone.herbalease.databinding.KeywordItemBinding

class KeywordAdapter : RecyclerView.Adapter<KeywordAdapter.ViewHolder>(){

    private val listKeyword = ArrayList<Keyword>()

    fun setListKeyword(key : List<Keyword>){
        key.forEach {
            listKeyword.add(it)
        }
    }
    class ViewHolder(private val binding: KeywordItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(preview: Keyword) {
            binding.keyword.text = preview.keyword
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = KeywordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listKeyword.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listKeyword[position])
    }
}