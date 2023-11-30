package com.amazing.android.svap_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R

class HomePagerAdapter(private val images: List<Int>) : RecyclerView.Adapter<HomePagerAdapter.MainPagerHolder>() {

    inner class MainPagerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_main_pager_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPagerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_pager_item,parent,false)
        return MainPagerHolder(view)
    }

    override fun onBindViewHolder(holder: MainPagerHolder, position: Int) {
        holder.imageView.setImageResource(images[position%images.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}