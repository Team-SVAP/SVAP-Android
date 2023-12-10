package com.amazing.android.svap_android.feature.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.databinding.ActivityDetailBinding
import com.amazing.android.svap_android.type.Types
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDataSever()
        binding.ibDetailBack.setOnClickListener { finish() }
    }

    private fun initViewPager(imgList:List<String>) {
        binding.vpDetail.adapter = DetailPagerAdapter(imgList,baseContext)
        binding.vpDetail.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun initDataSever() {
        val id = intent.extras?.getLong("Id")
        if(id!= null) {
            petitionApi.detailShow(id = id).enqueue(object :Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    when(response.code()) {
                        200-> response.body()?.let { setData(it) }
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("TEST","e"+t)
                    Toast.makeText(baseContext,R.string.fail_sever,Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setData(data: DetailResponse) {
        binding.apply {
            when(data.types) {
                Types.SCHOOL-> tvDetailTag.text = "#학교_${data.location}"
                else -> tvDetailTag.text = "#기숙사_${data.location}"
            }
            tvDetailTitle.text = data.title
            tvDetailDate.text = data.dateTime
            tvDetailContent.text = data.content
            tvDetailView.text = "조회수 ${data.viewCounts}"
            if(data.voted){
                btnDetailAgree.setBackgroundResource(R.drawable.disable_btn)
                btnDetailAgree.text = "찬성 취소"
            }else{
                btnDetailAgree.setBackgroundResource(R.drawable.able_btn)
                btnDetailAgree.text = "찬성"
            }
        }
        data.imgUrl?.let { initViewPager(it) }
        //initViewPager(data.imgUrl)
        Log.d("TEST","ssss")
    }
}