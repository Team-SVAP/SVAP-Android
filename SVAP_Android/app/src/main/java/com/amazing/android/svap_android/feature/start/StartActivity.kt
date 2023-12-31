package com.amazing.android.svap_android.feature.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amazing.android.svap_android.databinding.ActivityStartBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.signup.SignUpActivity

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movePage()
    }

    private fun movePage() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.tvStartGoSignUp.setOnClickListener {
            val intent = Intent(baseContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
