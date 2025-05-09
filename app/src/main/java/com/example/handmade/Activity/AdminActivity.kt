package com.example.handmade.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.handmade.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtHello.text = "Hello Admin 👑"
    }
}
