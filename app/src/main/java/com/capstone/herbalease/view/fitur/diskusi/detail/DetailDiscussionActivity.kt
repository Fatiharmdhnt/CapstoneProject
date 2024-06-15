package com.capstone.herbalease.view.fitur.diskusi.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.capstone.herbalease.R

class DetailDiscussionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_discussion)
    }

    companion object{
        const val EXTRA_DISCUSSION = "extra_discussion"
    }

}