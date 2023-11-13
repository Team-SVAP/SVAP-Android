package com.amazing.android.svap_android.feature.signup.signupId

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.AuthAPI
import com.amazing.android.svap_android.databinding.FragmentSignUpIdBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.signup.signupPw.SignUpPwFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpIdFragment : Fragment() {

    lateinit var binding: FragmentSignUpIdBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: AuthAPI = retrofit.create(AuthAPI::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNextBtn()
        gotoLogin()
    }

    private fun gotoLogin() {
        binding.tvSignupIdLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initNextBtn() {
        binding.etSignupIdId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnSignupIdNext.isEnabled = binding.etSignupIdId.text.isNotEmpty()
                if (binding.etSignupIdId.text.isEmpty()) {
                    binding.btnSignupIdNext.setBackgroundResource(R.drawable.disable_btn)
                } else {
                    binding.btnSignupIdNext.setBackgroundResource(R.drawable.able_btn)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSignupIdNext.setOnClickListener {
            val accountId = binding.etSignupIdId.text.toString()
            sever(accountId)
        }
    }

    // 1. Callback
    // 2. Response
    // 3. data class

    private fun sever(accountId: String) {
        api.ckId(
            SignUpIdRequest(
                accountId = accountId,
            )
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response.code()) {
                    200 -> {
                        val fragmentTransaction: FragmentTransaction? =
                            activity?.supportFragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.fl_signup, SignUpPwFragment());
                        fragmentTransaction?.commit()
                    }
                    409 -> {
                        binding.tvSignupIdCheck.text =
                            resources.getString(R.string.use_another_user)
                    }
                    400, 500 -> {
                        binding.tvSignupIdCheck.text = resources.getString(R.string.check_id)
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}