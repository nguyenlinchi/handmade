package com.example.handmade

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.handmade.Activity.AdminActivity
import com.example.handmade.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOk.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPass.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            loginViewModel.login(email, password)
        }

        binding.btnDk.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginViewModel.loginResult.observe(this, Observer { response ->
            if (response != null && response.success) {
                val user = response.user
                val level = user?.level ?: 0

                // Lưu thông tin người dùng vào SharedPreferences
                getSharedPreferences("USER_SESSION", MODE_PRIVATE).edit().apply {
                    putString("USER_NAME", user?.user_name)
                    putInt("USER_LEVEL", level)
                    putInt("USER_ID", user?.id ?: -1)  // Lưu userId vào SharedPreferences
                    apply()
                }

                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                // Điều hướng theo quyền
                if (level == 1) {
                    startActivity(Intent(this, AdminActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            } else {
                Toast.makeText(this, response?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
