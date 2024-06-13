package com.capstone.herbalease.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.capstone.herbalease.data.model.RegisterRequest
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.login.LoginActivity
import com.capstone.herbalease.di.Result
import com.capstone.herbalease.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory(this)
    }
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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

    private fun showToast(message: String) {
        Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            signupButton.isVisible = !isLoading
            progressbar.isVisible = isLoading
        }
    }

    private fun setupAction() {
        binding.apply {
            signupButton.setOnClickListener {
                when {
                    nameEditText.error.isNullOrEmpty().not() || nameEditText.text.isNullOrEmpty() ->
                        showToast("Fill name correctly!")

                    emailEditText.error.isNullOrEmpty().not() || emailEditText.text.isNullOrEmpty() ->
                        showToast("Fill email correctly!")

                    password.error.isNullOrEmpty().not() || password.text.isNullOrEmpty() ->
                        showToast("Fill password correctly!")

                    else -> {
                        val registerData = RegisterRequest(nameEditText.text.toString(),
                            emailEditText.text.toString(),
                            password.text.toString())
                        viewModel.register(
                            registerData
                        ).observe(this@SignupActivity) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    val email = binding.emailEditText.text.toString()
                                    AlertDialog.Builder(this@SignupActivity).apply {
                                        setTitle("Yeah!")
                                        setMessage("Your account with $email is done. Click next for login.")
                                        setPositiveButton("Next") { _, _ ->
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    showToast(result.error)
                                }
                            }
                        }
                    }
                }
            }
            logInPromptTextView.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
