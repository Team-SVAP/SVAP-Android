package com.amazing.android.svap_android.feature.showPetition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.databinding.FragmentShowPetitionBinding
import com.amazing.android.svap_android.type.AccessTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ShowPetitionFragment : Fragment() {

    private lateinit var binding: FragmentShowPetitionBinding
    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    private val listOfType = ArrayList<AccessTypesModel>()
    private lateinit var typeAdapter: ShowSpinnerAdapter

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
        initSpinnerHandler()
        initSpinner()
        selectType()
    }

    private fun initSpinner() {
        val recent = AccessTypesModel("최신순으로 보기", "recent")
        listOfType.add(recent)

        val vote = AccessTypesModel("투표순으로 보기", "vote")
        listOfType.add(vote)

        val approval = AccessTypesModel("승인된 청원 보기", "approval")
        listOfType.add(approval)

        val waiting = AccessTypesModel("검토중인 청원 보기", "waiting")
        listOfType.add(waiting)

        typeAdapter = ShowSpinnerAdapter(requireContext(), R.layout.petition_type_item, listOfType)
        binding.spinnerShowPetition.adapter = typeAdapter
    }

    private fun initSpinnerHandler() {
        binding.spinnerShowPetition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //
                    val show =
                        binding.spinnerShowPetition.getItemAtPosition(position) as AccessTypesModel
                    when (show.type) {
                        //"recent" -> //
                        //"vote" -> //
                        //"approval" -> //
                        //"waiting" -> //

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //
                }
            }
    }

    private fun selectType() {
        binding.apply {
            tvShowPetitionAll.setOnClickListener {
                changeType(
                    tvShowPetitionAll,
                    tvShowPetitionSchool,
                    tvShowPetitionDormitory
                )
            }
            tvShowPetitionDormitory.setOnClickListener {
                changeType(
                    tvShowPetitionDormitory,
                    tvShowPetitionSchool,
                    tvShowPetitionAll
                )
            }
            tvShowPetitionSchool.setOnClickListener {
                changeType(
                    tvShowPetitionSchool,
                    tvShowPetitionAll,
                    tvShowPetitionDormitory
                )
            }
        }
    }

    private fun changeType(select: TextView, other: TextView, another: TextView) {
        select.setTextColor(resources.getColor(R.color.main_1))
        other.setTextColor(resources.getColor(R.color.gray_400))
        another.setTextColor(resources.getColor(R.color.gray_400))
    }

    private fun initData() {
        petitionApi.sortPetitionAll(accessTypes = AccessTypes.NORMAL).enqueue(object :
            Callback<List<SortAllResponse>> {
            override fun onResponse(
                call: Call<List<SortAllResponse>>,
                response: Response<List<SortAllResponse>>
            ) {
                //
                Log.d("TEST", "s" + response.body())
            }

            override fun onFailure(call: Call<List<SortAllResponse>>, t: Throwable) {
                //
            }
        })
    }
}