package com.amazing.android.svap_android.feature.showPetition

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.feature.main.MainActivity

class ShowBottomDialogAdapter(private val itemList: MutableList<AccessTypesModel>) : RecyclerView.Adapter<ShowBottomDialogAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowBottomDialogAdapter.Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_bottomsheet_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        Log.d("TEST", "xx" + itemList)
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item.name
        holder.checkVIew.visibility = if(item.isClicked) View.VISIBLE else View.INVISIBLE
        //holder.bind(item)
        holder.itemView.setOnClickListener {
            //listener.onItemClick(item)
            itemList.forEach {it.isClicked = false}
            item.isClicked = true
            notifyDataSetChanged()
            //val sortTag =
            ShowPetitionFragment().changeSortTag(item.accessTypes)
        }
    }

    fun setItem(items: MutableList<AccessTypesModel>) {
        if (items.isNotEmpty()) {
            Log.d("TEST","a"+items.size)
            //itemList = items
            notifyDataSetChanged()
            Log.d("TEST","c"+itemList.size)
        }
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tv_bottom_sheet_show_item)
        val checkVIew: ImageView = view.findViewById(R.id.iv_bottom_sheet_show_item_check)
    }
}