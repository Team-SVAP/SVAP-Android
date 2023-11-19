package com.amazing.android.svap_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.amazing.android.svap_android.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationMenu()
    }

    private fun initNavigationMenu() {
        val drawerLayout = binding.mainDrawerLayout
        val navView = binding.mainNavigationView

        navView.setNavigationItemSelectedListener(this)

        val navMenu = binding.tbMain.ibToolBarMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val headerView = navView.getHeaderView(0)
        val closeBtn = headerView.findViewById<ImageView>(R.id.iv_header_close)
        closeBtn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
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
            R.id.menu_item1 -> Toast.makeText(this, "메뉴1", Toast.LENGTH_SHORT).show()
        }
        return false
    }
}