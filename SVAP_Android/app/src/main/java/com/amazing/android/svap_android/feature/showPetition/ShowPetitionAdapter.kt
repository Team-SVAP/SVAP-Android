package com.amazing.android.svap_android.feature.showPetition

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

class ShowPetitionAdapter(
    private val itemList: List<SortPetitionResponse>,
    private val context: Context
) :
    RecyclerView.Adapter<ShowPetitionAdapter.ShowViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowPetitionAdapter.ShowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.show_petition_item, parent, false)
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowPetitionAdapter.ShowViewHolder, position: Int) {
        holder.title.text = itemList[position].title
        holder.time.text = itemList[position].dateTime
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
        return itemList.size
    }

    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_show_petition_item_title)
        val time: TextView = itemView.findViewById(R.id.tv_show_petition_item_date)
        val tag: TextView = itemView.findViewById(R.id.tv_show_petition_item_tag)
    }
}