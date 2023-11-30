package com.amazing.android.svap_android.feature.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.databinding.FragmentHomeBinding
import com.amazing.android.svap_android.feature.main.PopularPetitionResponse
import com.amazing.android.svap_android.feature.showPetition.ShowPetitionActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
        initViewPager()
        initPopularPetition()
    }

    private fun initSearch() {
        binding.svHomeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //검색 버튼 누를때
                if (query != null) performSearch(query)
                //performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //검색창에서 글자 변경될때
                return true
            }
        })
    }

    private fun performSearch(text: String) {
        val intent = Intent(context, ShowPetitionActivity::class.java)
        intent.putExtra("search", text)
        startActivity(intent)
    }

    private fun initViewPager() {
        viewPager2 = binding.vpHome
        binding.vpHome.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val images =
            listOf(R.drawable.main_pager_1, R.drawable.main_pager_2, R.drawable.main_pager_3)
        val adapter = HomePagerAdapter(images)
        viewPager2.adapter = adapter

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewPager2.currentItem = (viewPager2.currentItem + 1) % images.size
                handler.postDelayed(this, 3000)
            }
        }, 3000)
    }

    private fun initPopularPetition() {
        petitionApi.popularPetition().enqueue(object : Callback<PopularPetitionResponse> {
            override fun onResponse(
                call: Call<PopularPetitionResponse>,
                response: Response<PopularPetitionResponse>
            ) {
                binding.tvHomeTitle.text = response.body()?.title
                binding.tvHomeContent.text = response.body()?.content
            }

            override fun onFailure(call: Call<PopularPetitionResponse>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}