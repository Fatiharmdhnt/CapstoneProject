package com.capstone.herbalease.view.fitur.diskusi.post

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.herbalease.R
import com.capstone.herbalease.data.pref.ForumDiscussion
import com.capstone.herbalease.data.pref.Keyword
import com.capstone.herbalease.databinding.ActivityAddDicussionBinding
import com.capstone.herbalease.di.FakeData
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.adapter.KeywordAdapter

class AddDicussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDicussionBinding
    private val keywords = mutableListOf<Keyword>()
    private lateinit var adapter: KeywordAdapter
    private var uploadedImageView: ImageView? = null
    private var imageUri: Uri? = null
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
    private val viewModel by viewModels<AddDiscussionViewModel> {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDicussionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = KeywordAdapter()
        binding.keyword.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.keyword.adapter = adapter

        addDiscussion()

    }

    private fun addDiscussion() {
        binding.postDiscussion.isEnabled = false

        // Set Keyword
        binding.addKeyword.setOnClickListener {
            showAddKeywordDialog()
        }

        // Set Image
        binding.uploadPhotoFrame.setOnClickListener {
            openGalleryForImage()
        }

        // Enable button only when both fields are not empty
        binding.judul.addTextChangedListener {
            validateFields()
        }
        binding.deskripsi.addTextChangedListener {
            validateFields()
        }

        // Set onClickListener for the postDiscussion button
        binding.postDiscussion.setOnClickListener {
            if (imageUri != null) {
                postDiscussion()
            } else {
                makeToast("Please upload an image first.")
            }
        }
    }

    private fun validateFields() {
        binding.postDiscussion.isEnabled = !binding.judul.text.isNullOrEmpty() && !binding.deskripsi.text.isNullOrEmpty()
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
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        val imageBitmap = imageUri?.let {
            viewModel.resizeAndCompressImage(it, contentResolver, 1080, 1080, 1_000_000)
        }
        binding.uploadedImage.setImageBitmap(imageBitmap)
        binding.uploadedImage.visibility = ImageView.VISIBLE
        binding.uploadText.visibility = TextView.GONE
    }

    private fun postDiscussion() {
        viewModel.getSession()
        viewModel.userSession.observe(this, Observer {
            imageUri?.let { uri ->
                viewModel.postDiscussion(
                    binding.judul.text.toString(),
                    uri,
                    binding.deskripsi.text.toString(),
                    keywords,
                    this
                )
            }
        })
        viewModel.responseAddDiscussion.observe(this, Observer {
            if (it != null){
                makeToast("Berhasil menambah diskusi")
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                makeToast("Gagal Menambahkan Dikusi")
            }
        })
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
