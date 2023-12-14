package com.amazing.android.svap_android.feature.signup.signupName

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.UserApi
import com.amazing.android.svap_android.databinding.FragmentSignUpNameBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.main.MainActivity
import com.amazing.android.svap_android.feature.signup.SignUpRequest
import com.amazing.android.svap_android.feature.signup.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpNameFragment : Fragment() {

    lateinit var binding: FragmentSignUpNameBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: UserApi = retrofit.create(UserApi::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            val accountId = arguments?.getString("accountId")
            val password = arguments?.getString("password")
            if (accountId != null && password != null) sever(username, accountId, password) else {
                Toast.makeText(context, "이름 비번 널", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sever(name: String, accountId: String, password: String) {
        api.signup(
            SignUpRequest(
                username = name,
                accountId = accountId,
                password = password,
            )
        ).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        Toast.makeText(context, R.string.success_signup, Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }

                    400, 500 -> {
                        binding.tvSignupNameCheck.text = resources.getString(R.string.name_check)
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
