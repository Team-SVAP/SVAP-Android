package com.amazing.android.svap_android.feature.myPetition

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.feature.detail.DetailActivity
import com.amazing.android.svap_android.type.Types

class MyPetitionAdapter(
    private val itemList: List<MyPetitionResponse>,
    private val context: Context
) :
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
        holder.date.text = itemList[position].dateTime
        holder.content.text = itemList[position].content
        when (itemList[position].types) {
            Types.SCHOOL -> holder.tag.text = "#학교_${itemList[position].location}"
            else -> holder.tag.text = "#기숙사_${itemList[position].location}"
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Id", itemList[position].id)
            context.startActivity(intent)
        }
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
