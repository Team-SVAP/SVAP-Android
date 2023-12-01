package com.amazing.android.svap_android.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.databinding.ActivityMainBinding
import com.amazing.android.svap_android.feature.home.HomeFragment
import com.amazing.android.svap_android.feature.myPage.MyPageFragment
import com.amazing.android.svap_android.feature.showPetition.ShowPetitionFragment
import com.amazing.android.svap_android.feature.write.WritePetitionFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    private fun initNavigation() {
        val homeFragment = HomeFragment()
        val writeFragment = WritePetitionFragment()
        val showFragment = ShowPetitionFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(homeFragment)
        binding.navHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_home -> {
                    replaceFragment(homeFragment)
                    true
                }

                R.id.main_menu_write -> {
                    replaceFragment(writeFragment)
                    true
                }

                R.id.main_menu_show -> {
                    replaceFragment(showFragment)
                    true
                }

                R.id.main_menu_my_page -> {
                    replaceFragment(myPageFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_main, fragment)
            commit()
        }
    }
}


