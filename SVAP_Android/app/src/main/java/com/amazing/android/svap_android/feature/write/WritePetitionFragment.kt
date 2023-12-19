package com.amazing.android.svap_android.feature.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazing.android.svap_android.R
import com.amazing.android.svap_android.api.ApiProvider
import com.amazing.android.svap_android.api.PetitionApi
import com.amazing.android.svap_android.databinding.FragmentWritePetitionBinding
import com.amazing.android.svap_android.type.Types
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class WritePetitionFragment : Fragment() {
    private lateinit var binding: FragmentWritePetitionBinding
    private lateinit var typeAdapter: TypeSpinnerAdapter
    private val listOfType = ArrayList<PetitionTypeModel>()

    private val retrofit: Retrofit = ApiProvider.getInstance()
    private val petitionApi: PetitionApi = retrofit.create(PetitionApi::class.java)

    private var typeState = false
    private var imgUriList: MutableList<String> = ArrayList()
    private lateinit var typesTag: Types

    private val PERMISSIONS_REQUEST_CODE = 1000
    private val GALLERY_REQUEST_CODE = 1001

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_MEDIA_IMAGES)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritePetitionBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()
        initSpinnerHandler()
        checkInput()
        checkTextChange()
        binding.ibWritePetitionGallery.setOnClickListener { checkPermissions() }
        binding.tvWritePetitionComplete.setOnClickListener { postPetition() }
    }

    private fun checkTextChange() {
        binding.etWritePetitionTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkInput()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.etWritePetitionContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkInput()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etWritePetitionLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkInput()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun checkInput() {
        val title = binding.etWritePetitionTitle.text.toString()
        val type = binding.etWritePetitionLocation.text.toString()
        val content = binding.etWritePetitionContent.text.toString()

        if (title.isNotEmpty() && type.isNotEmpty() && content.isNotEmpty() && typeState && imgUriList.isNotEmpty()) {
            binding.tvWritePetitionComplete.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_1
                )
            )
            binding.tvWritePetitionComplete.isEnabled = true
        } else {
            binding.tvWritePetitionComplete.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray_600
                )
            )
            binding.tvWritePetitionComplete.isEnabled = false
        }
    }

    private fun postPetition() {
        val accessToken = context?.getSharedPreferences("token", 0)?.getString("accessToken", null)
        if (accessToken != null) {
            petitionApi.postWritePetition(
                accessToken = "Bearer $accessToken",
                WritePetitionRequest(
                    title = binding.etWritePetitionTitle.text.toString(),
                    content = binding.etWritePetitionContent.text.toString(),
                    types = typesTag,
                    location = binding.etWritePetitionLocation.text.toString(),
                    imageUrl = imgUriList
                )
            ).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    when (response.code()) {
                        201 -> {
                            Toast.makeText(context, "청원이 작성되었습니다.", Toast.LENGTH_SHORT).show()
                            binding.apply {
                                etWritePetitionTitle.text = null
                                etWritePetitionContent.text = null
                                etWritePetitionLocation.text = null
                            }
                            imgUriList.clear()
                            setImgAdapter()
                        }

                        400 -> {
                            Toast.makeText(context, "형식이 올바르지 않습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, R.string.fail_sever, Toast.LENGTH_SHORT).show()
                }
            })
        }
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
                    val type =
                        binding.spinnerWritePetition.getItemAtPosition(position) as PetitionTypeModel
                    if (type.name != "종류 선택") {
                        binding.tvWritePetitionType.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_700
                            )
                        )
                        typeState = true
                        when (type.name) {
                            "학교 청원" -> typesTag = Types.SCHOOL
                            "기숙사 청원" -> typesTag = Types.DORMITORY
                        }
                        checkInput()
                        Log.d("TEST", "s" + type.name)
                    } else {
                        binding.tvWritePetitionType.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_400
                            )
                        )
                        typeState = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun initSpinner() {
        val default = PetitionTypeModel("종류 선택")
        listOfType.add(default)

        val school = PetitionTypeModel("학교 청원")
        listOfType.add(school)

        val dormitory = PetitionTypeModel("기숙사 청원")
        listOfType.add(dormitory)

        typeAdapter = TypeSpinnerAdapter(requireContext(), R.layout.petition_type_item, listOfType)
        binding.spinnerWritePetition.adapter = typeAdapter

        binding.spinnerWritePetition.setSelection(0)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                REQUIRED_PERMISSIONS[0]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery() // 권한이 이미 허용된 경우
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE) // 권한 요청
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery() // 권한이 허용된 경우
            } else {
                // 권한이 거부된 경우에 대한 처리
                moveSetting()
                Log.d("TEST", "거부")
            }
        }
    }

    private fun moveSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    private fun openGallery() {
        Log.d("TEST", "오픈")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            imgUriList.add(uri.toString())
            checkInput()
            setImgAdapter()
        }
    }

    private fun setImgAdapter() {
        val recyclerView = binding.rvWritePetition
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = WriteImageAdapter(imgUriList)
    }
}