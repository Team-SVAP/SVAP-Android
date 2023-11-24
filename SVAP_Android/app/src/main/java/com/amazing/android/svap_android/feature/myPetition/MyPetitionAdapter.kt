package com.amazing.android.svap_android.feature.myPetition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R

class MyPetitionAdapter(private val itemList: List<MyPetitionResponse>) :
    RecyclerView.Adapter<MyPetitionAdapter.MyPetitionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPetitionAdapter.MyPetitionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_petition_item, parent, false)
        return MyPetitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPetitionViewHolder, position: Int) {
        holder.title.text = itemList[position].title
        holder.date.text = itemList[position].dateTime.toString()
        holder.content.text = itemList[position].content
        holder.tag.text = itemList[position].location
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class MyPetitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_my_petition_item_title)
        val date: TextView = itemView.findViewById(R.id.tv_my_petition_item_date)
        val content: TextView = itemView.findViewById(R.id.tv_my_petition_item_content)
        val tag: TextView = itemView.findViewById(R.id.tv_my_petition_item_tag)
    }
}