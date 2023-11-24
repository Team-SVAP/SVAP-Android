package com.amazing.android.svap_android.feature.myPetition

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.UserApi
import com.amazing.android.svap_android.databinding.ActivityMyPetitionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyPetitionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPetitionBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val userApi: UserApi = retrofit.create(UserApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPetitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initToken()
        binding.rvMyPetition.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun setAdapter(dataList: List<MyPetitionResponse>) {
        val adapter = MyPetitionAdapter(dataList)

        binding.rvMyPetition.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initToken() {
        val accessToken = getSharedPreferences("token", 0).getString("accessToken", null)
        if (accessToken != null) {
            initData(accessToken)
        } else {
            //토큰 없음
            Log.d("TEST", "토큰 전달 안됨")
        }
    }

    private fun initData(accessToken: String) {
        userApi.showMyPetition(accessToken = "Bearer $accessToken")
            .enqueue(object : Callback<List<MyPetitionResponse>> {
                override fun onResponse(
                    call: Call<List<MyPetitionResponse>>,
                    response: Response<List<MyPetitionResponse>>
                ) {
                    val myPetitionResponse = response.body()

                    if (myPetitionResponse != null) {
                        setAdapter(myPetitionResponse)
                    }
                }

                override fun onFailure(call: Call<List<MyPetitionResponse>>, t: Throwable) {
                    Toast.makeText(baseContext, R.string.fail_sever, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun initToolbar() {
        val toolbar = binding.tbMyPetition
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}