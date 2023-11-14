package com.amazing.android.svap_android.feature.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.databinding.ActivitySignUpBinding
import com.amazing.android.svap_android.feature.signup.signupId.SignUpIdFragment

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmentManager();
    }

    private fun initFragmentManager() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_signup, SignUpIdFragment());
        fragmentTransaction.commit()
    }
}


