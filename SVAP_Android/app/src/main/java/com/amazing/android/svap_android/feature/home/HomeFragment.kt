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
import com.amazing.android.svap_android.feature.detail.DetailActivity
import com.amazing.android.svap_android.feature.showPetition.ShowPetitionFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    private lateinit var viewPager2: ViewPager2
    private var id: Long = 0

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
        moveDetailPage()
    }

    private fun moveDetailPage() {
        binding.tvHomeMore.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Id", id)
            startActivity(intent)
        }
    }

    private fun initSearch() {
        binding.svHomeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun performSearch(text: String) {
        val bundle = Bundle()
        bundle.putString("search", text)

        val showPetitionFragment = ShowPetitionFragment()
        showPetitionFragment.arguments = bundle

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_main, showPetitionFragment)
        transaction?.commit()
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
                when (response.code()) {
                    200 -> {
                        binding.tvHomeTitle.text = response.body()?.title
                        binding.tvHomeContent.text = response.body()?.content
                        id = response.body()!!.id
                    }
                }
            }

            override fun onFailure(call: Call<PopularPetitionResponse>, t: Throwable) {
                Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
