package com.amazing.android.svap_android.feature.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amazing.android.svap_android.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}