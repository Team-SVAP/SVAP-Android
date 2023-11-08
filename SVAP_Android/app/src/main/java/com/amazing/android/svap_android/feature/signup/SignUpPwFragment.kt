package com.amazing.android.svap_android.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.AuthAPI
import com.amazing.android.svap_android.databinding.FragmentSignUpPwBinding
import com.amazing.android.svap_android.feature.signup.signupId.CkPasswordRequest
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
        // Inflate the layout for this fragment
        binding = FragmentSignUpPwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNextBtn();
    }

    private fun initNextBtn() {
        binding.btnSignupPwNext.setOnClickListener {
            val pw = binding.etSignupPwPw.text.toString()
            val pwCheck = binding.etSignupPwCheck.text.toString()
            if (pw != pwCheck) {
                binding.tvSignupPwNo.visibility = View.VISIBLE
            } else {
                sever(pw)
            }
        }
    }

    private fun sever(pw: String) {
        api.ckPassword(
            CkPasswordRequest(
                password = pw,
            )
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                response.code()
                when (response.code()) {
                    200 -> {
                        //다음
                    }

                    400 -> {
                        binding.tvSignupPwNo.visibility = View.INVISIBLE
                        binding.tvSignupPwCheck.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}