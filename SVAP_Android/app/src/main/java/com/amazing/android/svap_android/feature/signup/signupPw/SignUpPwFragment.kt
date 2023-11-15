package com.amazing.android.svap_android.feature.signup.signupPw

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
import com.amazing.android.svap_android.databinding.FragmentSignUpPwBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.signup.signupName.SignUpNameFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpPwFragment : Fragment() {

    lateinit var binding: FragmentSignUpPwBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: AuthAPI = retrofit.create(AuthAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNextBtn()
        gotoLogin()
    }

    private fun gotoLogin() {
        binding.tvSignupPwLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initNextBtn() {
        binding.etSignupPwPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBtn()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.etSignupPwCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBtn()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.btnSignupPwNext.setOnClickListener {
            val pw = binding.etSignupPwPw.text.toString()
            val pwCheck = binding.etSignupPwCheck.text.toString()
            if (pw != pwCheck) {
                binding.tvSignupPwCheck.text = resources.getString(R.string.password_no)
            } else {
                sever(pw)
            }
        }
    }

    private fun changeBtn() {
        val pw = binding.etSignupPwPw.text.toString()
        val pwCheck = binding.etSignupPwCheck.text.toString()
        binding.btnSignupPwNext.isEnabled = !(pw.isEmpty() || pwCheck.isEmpty())
        if (pw.isEmpty() || pwCheck.isEmpty()) {
            binding.btnSignupPwNext.setBackgroundResource(R.drawable.disable_btn)
        } else {
            binding.btnSignupPwNext.setBackgroundResource(R.drawable.able_btn)
        }
    }

    private fun sever(pw: String) {
        api.ckPassword(
            SignUpPwRequest(
                password = pw,
            )
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when (response.code()) {
                    200 -> {
                        val accountId = arguments?.getString("accountId")

                        val bundle = Bundle()
                        bundle.putString("accountId",accountId)
                        bundle.putString("password",pw)

                        val fragmentTransaction: FragmentTransaction? =
                            activity?.supportFragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.fl_signup, SignUpNameFragment());
                        fragmentTransaction?.commit()
                    }
                    400, 500 -> {
                        binding.tvSignupPwCheck.text = resources.getString(R.string.check_password)
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}