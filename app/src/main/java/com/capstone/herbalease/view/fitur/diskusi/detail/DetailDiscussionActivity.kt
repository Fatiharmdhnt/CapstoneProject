package com.capstone.herbalease.view.fitur.diskusi.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.herbalease.data.model.Comments
import com.capstone.herbalease.data.model.ForumDiscussion
import com.capstone.herbalease.databinding.ActivityDetailDiscussionBinding
import com.capstone.herbalease.view.adapter.CommentAdapter
import com.capstone.herbalease.view.adapter.KeywordAdapter

class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var dataDiscussion : ForumDiscussion
    private lateinit var binding : ActivityDetailDiscussionBinding
    private lateinit var adapterKeyword : KeywordAdapter
    private lateinit var adapterComment : CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataDiscussion = intent.getSerializableExtra(EXTRA_DISCUSSION) as ForumDiscussion
        adapterKeyword = KeywordAdapter()
        adapterKeyword.setListKeyword(dataDiscussion.keyword)
        adapterComment.setComment(dataDiscussion.comments)
        setDiscussion()
    }


    private fun setDiscussion(){
        //Nama pemilik diskusi
        binding.userName.text = dataDiscussion.name

        //Foto profil pemilik diskusi
        Glide.with(binding.imageView.context).load(dataDiscussion.photoDiscussionUrl).centerCrop().into(binding.imageView)

        //judul diskusi
        binding.textView.text = dataDiscussion.title

        //deskripsi diskusi
        binding.description.text = dataDiscussion.description

        //gambar diskusi
        Glide.with(binding.shapeableImageView.context).load(dataDiscussion.photoProfileUrl).centerCrop().into(binding.shapeableImageView)

        //keyword
        binding.keyword.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.keyword.adapter = adapterKeyword

        //komentar
        binding.commentUser.layoutManager = LinearLayoutManager(this)
        binding.commentUser.adapter = adapterComment
    }

    companion object{
        const val EXTRA_DISCUSSION = "extra_discussion"
    }

}