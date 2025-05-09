package com.example.handmade.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.handmade.LoginActivity
import com.example.handmade.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_introduction) // Đảm bảo file XML là activity_intro.xml

        // Ánh xạ các view từ layout
        val startButton: Button = findViewById(R.id.start_btn)
        val signInText: TextView = findViewById(R.id.textView4)

        // Xử lý sự kiện khi nhấn "Let's Get Started"
        startButton.setOnClickListener {
            // Chuyển sang màn hình chính hoặc màn hình đăng ký
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Xử lý sự kiện khi nhấn "Sign In"
        signInText.setOnClickListener {
            // Chuyển sang màn hình đăng nhập
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
