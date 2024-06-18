package com.capstone.herbalease.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.capstone.herbalease.R
import com.capstone.herbalease.databinding.ActivityLoginBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.signup.SignupActivity
import com.capstone.herbalease.data.model.retrofit.LoginRequest
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.di.Result
import com.capstone.herbalease.view.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            loginButton.isVisible = !isLoading
            progressbar.isVisible = isLoading
        }
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                when {
                    emailEditText.error.isNullOrEmpty().not() || emailEditText.text.isNullOrEmpty() ->
                        showToast("Fill email correctly!")

                    password.error.isNullOrEmpty().not() || password.text.isNullOrEmpty() ->
                        showToast("Fill password correctly!")

                    else -> {
                        val loginData = LoginRequest(emailEditText.text.toString(),
                            password.text.toString())
                        viewModel.login(
                            loginData
                        ).observe(this@LoginActivity) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    val email = binding.emailEditText.text.toString()
                                    viewModel.saveSession(
                                        UserModel(
                                            email,
                                            result.data.loginResult!!.token
                                        )
                                    )
                                    val alertDialog = AlertDialog.Builder(this@LoginActivity).apply {
                                        setTitle("Login Berhasil")
                                        setMessage("Mari kita jelajahi dunia tanaman herbal yang menakjubkan hari ini")
                                        setPositiveButton("Lanjut") { _, _ ->
                                            val intent = Intent(context, MainActivity::class.java)
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(intent)
                                            finish()
                                        }
                                        create()
                                    }.show()

                                    val greenColor = ContextCompat.getColor(this@LoginActivity, R.color.green)
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(greenColor)
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

            signupPromptTextView.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left)
            }

        }
    }
}
