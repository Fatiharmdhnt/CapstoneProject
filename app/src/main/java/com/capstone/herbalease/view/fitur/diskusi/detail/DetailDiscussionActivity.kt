package com.capstone.herbalease.view.fitur.diskusi.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.herbalease.data.model.response.ForumDiscussion
import com.capstone.herbalease.databinding.ActivityDetailDiscussionBinding
import com.capstone.herbalease.di.FakeData
import com.capstone.herbalease.view.adapter.CommentAdapter
import com.capstone.herbalease.view.adapter.KeywordAdapter

class DetailDiscussionActivity : AppCompatActivity() {

    private lateinit var dataDiscussion : ForumDiscussion
    private lateinit var binding : ActivityDetailDiscussionBinding
    private lateinit var adapterKeyword : KeywordAdapter
    private lateinit var adapterComment : CommentAdapter
    private lateinit var viewModel: DetailDiscussionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailDiscussionViewModel::class.java)

        dataDiscussion = intent.getParcelableExtra<ForumDiscussion>(EXTRA_DISCUSSION)!!
        adapterKeyword = KeywordAdapter()
        adapterComment = CommentAdapter()
        adapterKeyword.setListKeyword(dataDiscussion.keyword)
        dataDiscussion.comments?.let { adapterComment.setComment(it) }
        setDiscussion()

        binding.buttonSendComment.setOnClickListener {
            postComment()
        }
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

    private fun postComment(){
        if (binding.editTextComment.text != null){
            viewModel.sendComment(binding.editTextComment.text.toString(), dataDiscussion.title)
            binding.editTextComment.text!!.clear()
            FakeData.discussionList.forEach {
                if (it.title == dataDiscussion.title){
                    it.comments?.let { it1 -> adapterComment.setComment(it1) }
                }
            }
        } else {
            makeToast("Tolong Isi Komentar Anda Terlebih Dahulu")
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val EXTRA_DISCUSSION = "extra_discussion"
    }

}