package com.amazing.android.svap_android.feature.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.databinding.FragmentSignUpIdBinding
import com.amazing.android.svap_android.databinding.FragmentSignUpPwBinding

class SignUpPwFragment : Fragment() {

    lateinit var binding: FragmentSignUpPwBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpPwBinding.inflate(inflater,container,false)
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
            if(pw != pwCheck) {
                binding.tvSignupPwNo.visibility = View.VISIBLE
            }else {
                sever()
            }
        }
    }

    private fun sever() {

    }
}