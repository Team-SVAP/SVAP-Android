package com.amazing.android.svap_android.feature.write

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.databinding.PetitionTypeItemBinding

class TypeSpinnerAdapter(
    context: Context,
    @LayoutRes private val resId: Int,
    private val values: MutableList<PetitionTypeModel>
) : ArrayAdapter<PetitionTypeModel>(context, resId, values) {

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            PetitionTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        binding.tvPetitionType.text = model.name
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            PetitionTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]

        binding.tvPetitionType.text = model.name

        if (position == 0) {
            binding.tvPetitionType.setTextColor(ContextCompat.getColor(context, R.color.gray_400))
        } else {
            binding.tvPetitionType.setTextColor(ContextCompat.getColor(context, R.color.gray_700))
        }
        return binding.root
    }
}