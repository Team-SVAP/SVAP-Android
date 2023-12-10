package com.amazing.android.svap_android.feature.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.bumptech.glide.Glide

class DetailPagerAdapter(private val imgList: List<String>, private val context: Context) :
    RecyclerView.Adapter<DetailPagerAdapter.DetailViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPagerAdapter.DetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_pager_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailPagerAdapter.DetailViewHolder, position: Int) {
        Glide.with(context)
            .load(imgList[position])
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    inner class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.iv_detail_item)
    }
}