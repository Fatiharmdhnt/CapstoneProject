package com.capstone.herbalease.view.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.herbalease.databinding.ActivityWelcomeBinding
import com.capstone.herbalease.view.MainActivity
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {

        viewModel.userSession.observe(this, Observer { user ->
            if (user != null && user.isLogin) {
                Log.d("welcome", user.toString())
                binding.loginButton.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }else{
                binding.loginButton.setOnClickListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        })

        viewModel.getSession()
    }
}