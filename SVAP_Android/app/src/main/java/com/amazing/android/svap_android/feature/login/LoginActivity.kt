package com.amazing.android.svap_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.AuthAPI
import com.amazing.android.svap_android.databinding.ActivityLoginBinding
import com.amazing.android.svap_android.feature.login.LoginRequest
import com.amazing.android.svap_android.feature.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val api: AuthAPI = retrofit.create(AuthAPI::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLoginBtn()
    }

    private fun initLoginBtn() {
        binding.run {
            btnLogin.setOnClickListener {
                val id = etLoginId.text.toString()
                val pw = etLoginPw.text.toString()

                if (id.isEmpty() || pw.isEmpty()) {
                    tvLoginCheck.visibility = View.VISIBLE
                } else {
                    login(id, pw);
                }
            }
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
                Toast.makeText(baseContext, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(baseContext, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }
}