package com.amazing.android.svap_android.feature.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazing.android.svap_android.MainActivity
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.UserApi
import com.amazing.android.svap_android.databinding.ActivityLoginBinding
import com.amazing.android.svap_android.feature.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: UserApi = retrofit.create(UserApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gotoSignup()
        initLoginBtn()
    }

    private fun gotoSignup() {
        binding.tvLoginGoSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initLoginBtn() {
        binding.etLoginId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBtn()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.etLoginPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeBtn()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.run {
            btnLogin.setOnClickListener {
                val id = etLoginId.text.toString()
                val pw = etLoginPw.text.toString()
                login(id, pw);
            }
        }
    }

    private fun changeBtn() {
        val id = binding.etLoginId.text.toString()
        val pw = binding.etLoginPw.text.toString()
        binding.btnLogin.isEnabled = !(id.isEmpty() || pw.isEmpty())
        if (id.isEmpty() || pw.isEmpty()) {
            binding.btnLogin.setBackgroundResource(R.drawable.disable_btn)
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.able_btn)
        }
    }

    private fun login(id: String, pw: String) {
        api.login(
            LoginRequest(
                accountId = id,
                password = pw,
            )
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when(response.code()) {
                    200-> {
                        Toast.makeText(baseContext, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    400-> {
                        binding.tvLoginCheck.text = resources.getString(R.string.check_id_pw)
                    }
                    401-> {
                        binding.tvLoginCheck.text = resources.getString(R.string.mismatch_password)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(baseContext, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }
}