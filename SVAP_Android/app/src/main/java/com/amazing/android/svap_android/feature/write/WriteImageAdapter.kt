package com.amazing.android.svap_android.feature.write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.bumptech.glide.Glide

class WriteImageAdapter(private val itemList: List<String>) :
    RecyclerView.Adapter<WriteImageAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WriteImageAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.write_img_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: WriteImageAdapter.ItemViewHolder, position: Int) {
        Glide.with(holder.view)
            .load(itemList[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_write_img)
    }
}
