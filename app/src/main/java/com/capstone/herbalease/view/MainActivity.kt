package com.capstone.herbalease.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.herbalease.R
import com.capstone.herbalease.databinding.ActivityMainBinding
import com.capstone.herbalease.view.fitur.scan.CameraActivity
import com.capstone.herbalease.view.welcome.WelcomeActivity
import com.capstone.herbalease.view.welcome.WelcomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val viewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    android.util.Log.e("FTEST", "1")
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_home)
                }

                R.id.navigation_favorite -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_favorite)
                }

                R.id.navigation_discuss -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_discuss)
                }

                R.id.navigation_profile -> {
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_profile)
                }
            }
            false
        }

        viewModel.userSession.observe(this, Observer { user ->
            if (user != null && user.isLogin) {

            }else{
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        })

        viewModel.getSession()

        binding.fabProcess.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

    }

}
