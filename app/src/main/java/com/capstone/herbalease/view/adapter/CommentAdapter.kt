package com.capstone.herbalease.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.herbalease.data.model.retrofit.Comments
import com.capstone.herbalease.databinding.CommentItemBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val listComment = ArrayList<Comments>()

    @SuppressLint("NotifyDataSetChanged")
    fun setComment(comment: List<Comments>) {
        listComment.clear()
        listComment.addAll(comment)
        notifyDataSetChanged()
    }

    class ViewHolder (private val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(preview: Comments){
            binding.userName.text = preview.name
            Glide.with(itemView).load(preview.photoProfileUrl).centerCrop().into(binding.shapeableImageView)
            binding.comment.text = preview.comments
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listComment.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listComment[position])
    }
}