package com.amazing.android.svap_android.feature.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.databinding.FragmentWritePetitionBinding
import com.amazing.android.svap_android.type.Type


class WritePetitionFragment : Fragment() {
    private lateinit var binding: FragmentWritePetitionBinding
    private lateinit var typeAdapter: TypeSpinnerAdapter
    private val listOfType = ArrayList<PetitionTypeModel>()

    private val REQUEST_CODE = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritePetitionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()
        initSpinnerHandler()
        binding.ibWritePetitionGallery.setOnClickListener { openGallery() }
    }

    private fun initSpinnerHandler() {
        binding.spinnerWritePetition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //클릭
                    val type =
                        binding.spinnerWritePetition.getItemAtPosition(position) as PetitionTypeModel
                    if (!type.name.equals("")) {
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun initSpinner() {
        val school = PetitionTypeModel("학교 청원", Type.SCHOOL)
        listOfType.add(school)

        val dormitory = PetitionTypeModel("기숙사 청원", Type.DORMITORY)
        listOfType.add(dormitory)

        typeAdapter = TypeSpinnerAdapter(requireContext(), R.layout.petition_type_item, listOfType)
        binding.spinnerWritePetition.adapter = typeAdapter
    }

    private fun openGallery() {
        //requestPermission()
        locationResultLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_CODE)
    }

    /*private fun requestPermission() {
            val locationResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if(!it) {
                Toast.makeText(this, "스토리지에 접근 권한을 허가해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        locationResultLauncher.launch(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }*/

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_CODE -> {
                data ?: return
                val uri = data.data as Uri
            }

            else -> {
                Toast.makeText(context, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val locationResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(context, "스토리지에 접근 권한을 허가해주세요", Toast.LENGTH_SHORT).show()
            }
        }

}