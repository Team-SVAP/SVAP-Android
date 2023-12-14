package com.amazing.android.svap_android.feature.myPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.UserApi
import com.amazing.android.svap_android.databinding.FragmentMyPageBinding
import com.amazing.android.svap_android.feature.login.LoginActivity
import com.amazing.android.svap_android.feature.myPetition.MyPetitionActivity
import com.amazing.android.svap_android.util.CustomDialog
import com.amazing.android.svap_android.util.DialogClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val userApi: UserApi = retrofit.create(UserApi::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMovePage()
        initUserData()
        showDialog()
    }

    private fun showDialog() {
        binding.tvMyPageLogout.setOnClickListener {
            val logoutDialog = CustomDialog(
                requireContext(),
                "로그아웃 하시겠습니까?",
                "다시 로그인 해야 합니다.",
                "확인",
                object : DialogClickListener {
                    override fun onYesBtnClick() {
                        logout()
                    }
                })
            logoutDialog.show()
        }

        binding.tvMyPageDelete.setOnClickListener {
            val deleteDialog = CustomDialog(
                requireContext(),
                "회원 탈퇴 하시겠습니까?",
                "계정이 삭제됩니다.",
                "확인",
                object : DialogClickListener {
                    override fun onYesBtnClick() {
                        deleteUser()
                    }
                })
            deleteDialog.show()
        }
    }

    private fun logout() {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun deleteUser() {
        val accessToken = context?.getSharedPreferences("token", 0)?.getString("accessToken", null)
        if (accessToken != null) {
            userApi.deleteUser(accessToken = accessToken).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        200 -> {
                            logout()
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun initUserData() {
        val accessToken = context?.getSharedPreferences("token", 0)?.getString("accessToken", null)
        if (accessToken != null) {
            userApi.myInfo(accessToken = accessToken).enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    when (response.code()) {
                        200 -> binding.tvMyPageName.text = response.body()?.userName
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun initMovePage() {
        binding.tvMyPageShow.setOnClickListener {
            val intent = Intent(context, MyPetitionActivity::class.java)
            startActivity(intent)
        }
    }
}
