package com.amazing.android.svap_android.feature.showPetition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R

class ShowBottomDialogAdapter(private val itemClickListener: (AccessTypesModel) -> Unit) :
    RecyclerView.Adapter<ShowBottomDialogAdapter.Holder>() {

    private var itemList: List<AccessTypesModel> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowBottomDialogAdapter.Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_bottomsheet_item, parent, false)
        return Holder(view)
    }

    fun submitList(newItems: List<AccessTypesModel>) {
        itemList = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item.name
        holder.checkVIew.visibility = if (item.isClicked) View.VISIBLE else View.INVISIBLE
        holder.itemView.setOnClickListener {
            //itemList.forEach { it.isClicked = false }
            val previousItem = itemList.find { it.isClicked}
            previousItem?.isClicked = false
            item.isClicked = true
            itemClickListener.invoke(item)
            notifyDataSetChanged()
        }
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tv_bottom_sheet_show_item)
        val checkVIew: ImageView = view.findViewById(R.id.iv_bottom_sheet_show_item_check)
    }
}