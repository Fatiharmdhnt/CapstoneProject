package com.capstone.herbalease.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.herbalease.data.model.ForumDiscussion
import com.capstone.herbalease.databinding.FragmentItemBinding

class DiscussionAdapter : RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

    private val listDiscussion = ArrayList<ForumDiscussion>()
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(data: ForumDiscussion)
    }

    fun setOnItemClickCallback(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(private val binding: FragmentItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(preview: ForumDiscussion, onItemClickCallback: OnItemClickListener) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClick(preview)
            }

            binding.userName.text = preview.name
            Glide.with(binding.photoProfile.context).load(preview.photoProfileUrl)
                .into(binding.photoProfile)
            binding.title.text = preview.title
            binding.description.text = preview.description

            // Initialize KeywordAdapter and set it to the RecyclerView
            val keywordAdapter = KeywordsAdapter()
            binding.keyword.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.keyword.adapter = keywordAdapter
            keywordAdapter.setListKeyword(preview.keyword)

            Glide.with(binding.imageDiscussion.context).load(preview.photoDiscussionUrl)
                .into(binding.imageDiscussion)
            binding.comments.text = "${preview.comments.size} Comments"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDiscussion[position], onItemClickListener!!)
    }

    override fun getItemCount(): Int = listDiscussion.size

    @SuppressLint("NotifyDataSetChanged")
    fun setListDiscussion(discussions: List<ForumDiscussion>) {
        listDiscussion.clear()
        listDiscussion.addAll(discussions)
        notifyDataSetChanged()
    }
}
