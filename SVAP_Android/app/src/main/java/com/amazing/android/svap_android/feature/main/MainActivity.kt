package com.amazing.android.svap_android.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.api.UserApi
import com.amazing.android.svap_android.databinding.ActivityMainBinding
import com.amazing.android.svap_android.feature.myPetition.MyPetitionActivity
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

private const val NUM_PAGES = 2
class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding: ActivityMainBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        initNavigationMenu()
        initPopularPetition()
        initViewPager()
    }

    private fun initViewPager() {
        //binding.vpMain = MainPagerAdapter(this)
        viewPager2 = binding.vpMain
        binding.vpMain.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val images = listOf(R.drawable.main_pager_1,R.drawable.main_pager_2)
        val adapter = MainPagerAdapter(images)
        viewPager2.adapter= adapter

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewPager2.currentItem = (viewPager2.currentItem + 1) % images.size
                handler.postDelayed(this, 3000)
            }
        },3000)
    }

    private fun initPopularPetition() {
        petitionApi.popularPetition().enqueue(object : Callback<PopularPetitionResponse> {
            override fun onResponse(
                call: Call<PopularPetitionResponse>,
                response: Response<PopularPetitionResponse>
            ) {
                binding.tvMainTitle.text = response.body()?.title
                binding.tvMainContext.text = response.body()?.content
            }

            override fun onFailure(call: Call<PopularPetitionResponse>, t: Throwable) {
                Toast.makeText(baseContext, R.string.fail_sever, Toast.LENGTH_SHORT).show()
                Log.d("TEST","ss"+t);
            }
        })
    }

    private fun initNavigationMenu() {
        val drawerLayout = binding.mainDrawerLayout
        val navView = binding.mainNavigationView

        navView.setNavigationItemSelectedListener(this)

        val navMenu = binding.tbMain.ibToolBarMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val headerView = navView.getHeaderView(0)
        val closeBtn = headerView.findViewById<ImageView>(R.id.iv_header_close)
        closeBtn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    //메인 액션바 옵션 설정 함수
    //툴바 세팅
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_nav_menu_list, menu)
        //커스텀 메뉴
        val settingIcon = menu?.findItem(R.id.iv_main_nav_header_setting)
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_item1 -> {
                val intent = Intent(this@MainActivity, MyPetitionActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }
}