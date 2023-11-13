package com.amazing.android.svap_android.feature.signup.signupName

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.AuthAPI
import com.amazing.android.svap_android.databinding.FragmentSignUpNameBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.signup.signupId.SignUpIdRequest
import com.amazing.android.svap_android.feature.signup.signupPw.SignUpPwFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpNameFragment : Fragment() {

    lateinit var binding: FragmentSignUpNameBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: AuthAPI = retrofit.create(AuthAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNextBtn()
        gotoLogin()
    }

    private fun gotoLogin() {
        binding.tvSignupNameLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initNextBtn() {

        binding.etSignupNameName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSignupIdNext.isEnabled = binding.etSignupNameName.text.isNotEmpty()
                if (binding.etSignupNameName.text.isEmpty()) {
                    binding.btnSignupIdNext.setBackgroundResource(R.drawable.disable_btn)
                } else {
                    binding.btnSignupIdNext.setBackgroundResource(R.drawable.able_btn)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSignupIdNext.setOnClickListener {
            val username = binding.etSignupNameName.text.toString()
            sever(username)
        }
    }

    private fun sever(name: String) {
        api.ckName(
            SignUpNameRequest(
                username = name
            )
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response.code()) {
                    200 -> {
                        //회원가입
                    }
                    400, 500 -> {
                        binding.tvSignupNameCheck.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}