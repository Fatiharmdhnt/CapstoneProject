package com.capstone.herbalease.view.fitur.diskusi.post

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.herbalease.R
import com.capstone.herbalease.data.model.response.ForumDiscussion
import com.capstone.herbalease.data.model.response.Keyword
import com.capstone.herbalease.databinding.ActivityAddDicussionBinding
import com.capstone.herbalease.di.FakeData
import com.capstone.herbalease.view.adapter.KeywordAdapter

class AddDicussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDicussionBinding
    private val keywords = mutableListOf<Keyword>()
    private lateinit var adapter: KeywordAdapter
    private lateinit var uploadedImageView: ImageView
    private var discussionList = mutableListOf<ForumDiscussion>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDicussionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = KeywordAdapter()
        binding.keyword.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.keyword.adapter = adapter

        addDiscussion()
    }

    private fun addDiscussion(){
        binding.postDiscussion.isEnabled = false
        //Set Keyword
        binding.addKeyword.setOnClickListener {
            showAddKeywordDialog()
        }

        //Set Image
        binding.uploadPhotoFrame.setOnClickListener{
            openGalleryForImage()
        }

        //Getting list from fake data (erase soon)
        discussionList = FakeData.discussionList.toMutableList()

        if (binding.judul != null){
            if (binding.deskripsi != null){
                binding.postDiscussion.isEnabled = true
                binding.postDiscussion.setOnClickListener {
                    postDiscussion()
                }
            }else{
                makeToast("isi Deskripsi terlebih dahulu")
            }
        } else {
            makeToast("Isi Judul terlebih dahulu")
        }
    }

    private fun showAddKeywordDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_keyword)

        val btnAdd = dialog.findViewById<Button>(R.id.btn_add)
        val keywordInput = dialog.findViewById<EditText>(R.id.keyword_input)

        btnAdd.setOnClickListener {
            val keyword = Keyword(keywordInput.text.toString().trim())
            keywords.add(keyword)
            adapter.setListKeyword(keywords)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            uploadedImageView.setImageURI(selectedImageUri)
            uploadedImageView.visibility = ImageView.VISIBLE
            binding.uploadText.visibility = TextView.GONE
        }
    }

    private fun postDiscussion(){
        val newDiscussion = ForumDiscussion(
            "benyud",
            "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
            binding.judul.text.toString(),
            binding.deskripsi.text.toString(),
            keywords,
            "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
            null
        )
        discussionList.add(newDiscussion)
        FakeData.discussionList = discussionList
        makeToast("Berhasil menambah dikusi")

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}