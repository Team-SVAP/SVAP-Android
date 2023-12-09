package com.amazing.android.svap_android.feature.showPetition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.type.AccessTypes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShowBottomDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_show_petition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val recyclerView = view.findViewById<RecyclerView>(R.id.rv_bottom_sheet_show)
        //recyclerView.layoutManager = LinearLayoutManager(context)
        //val adapter = ShowBottomDialogAdapter()

        //adapter.setItem(itemList)
        Log.d("TEST", "tt" + view)
        //recyclerView.layoutManager = LinearLayoutManager(context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_bottom_sheet_show)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val list = mutableListOf(
            AccessTypesModel("최신순",AccessTypes.NORMAL),
            AccessTypesModel("투표순",AccessTypes.VOTE),
            AccessTypesModel("승인된 청원",AccessTypes.APPROVAL),
            AccessTypesModel("검토중인 청원",AccessTypes.WAITING))

        recyclerView.adapter = ShowBottomDialogAdapter(list)

        /*recyclerView.adapter = ShowBottomDialogAdapter(list, object : OnItemClickListener {
            override fun onItemClick(item: AccessTypesModel) {
                (activity as ShowBottomDialogFragment)
            }

        })*/

        //adapter.setItem(list)
        //view.findViewById<RecyclerView>(R.id.rv_bottom_sheet_show).adapter = adapter
    }
}