package com.amazing.android.svap_android.feature.showPetition

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.databinding.FragmentShowPetitionBinding
import com.amazing.android.svap_android.feature.myPetition.MyPetitionAdapter
import com.amazing.android.svap_android.feature.myPetition.MyPetitionResponse
import com.amazing.android.svap_android.type.AccessTypes
import com.amazing.android.svap_android.type.Types
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ShowPetitionFragment : Fragment(){

    private lateinit var binding: FragmentShowPetitionBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)
    private var types:Types = Types.ALL
    private var accessTypes:AccessTypes = AccessTypes.NORMAL
    private lateinit var mcontext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowPetitionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        //initSpinnerHandler()
        //initSpinner()
        selectType()
        initBottomSheet()
        openBottomSheet()
        binding.rvShow.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }

    private fun openBottomSheet() {
        binding.tvShowPetitionSort.setOnClickListener {
            ShowBottomDialogFragment().show(parentFragmentManager,"ShowBottomDialogFragment")
        }
    }

    fun changeSortTag(accessTypes: AccessTypes) {
        this.accessTypes = accessTypes
        initData()
    }

    private fun initBottomSheet() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_bottom_sheet_show)
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }


    private fun selectType() {
        binding.apply {
            tvShowPetitionAll.setOnClickListener {
                changeType(
                    tvShowPetitionAll,
                    tvShowPetitionSchool,
                    tvShowPetitionDormitory
                )
                types = Types.ALL
                initData()
            }
            tvShowPetitionDormitory.setOnClickListener {
                changeType(
                    tvShowPetitionDormitory,
                    tvShowPetitionSchool,
                    tvShowPetitionAll
                )
                types = Types.DORMITORY
                initData()
            }
            tvShowPetitionSchool.setOnClickListener {
                changeType(
                    tvShowPetitionSchool,
                    tvShowPetitionAll,
                    tvShowPetitionDormitory
                )
                types = Types.SCHOOL
                initData()
            }
        }
    }

    private fun changeType(select: TextView, other: TextView, another: TextView) {
        select.setTextColor(resources.getColor(R.color.main_1))
        other.setTextColor(resources.getColor(R.color.gray_400))
        another.setTextColor(resources.getColor(R.color.gray_400))
    }

    private fun initData() {
        petitionApi.sortPetition(types = types,accessTypes = accessTypes).enqueue(object :
            Callback<List<SortPetitionResponse>> {
            override fun onResponse(
                call: Call<List<SortPetitionResponse>>,
                response: Response<List<SortPetitionResponse>>
            ) {
                //
                when(response.code()) {
                    200-> response.body()?.let { setAdapter(it) }
                }
                Log.d("TEST", "s" + response.body())
            }

            override fun onFailure(call: Call<List<SortPetitionResponse>>, t: Throwable) {
                //
            }
        })
    }

    private fun setAdapter(dataList: List<SortPetitionResponse>) {
        val adapter = ShowPetitionAdapter(dataList,mcontext)

        binding.rvShow.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}