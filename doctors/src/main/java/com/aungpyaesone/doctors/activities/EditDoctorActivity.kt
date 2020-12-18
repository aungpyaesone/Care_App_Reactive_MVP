package com.aungpyaesone.doctors.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.EditProfilePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.EditProfilePresenterImpl
import com.aungpyaesone.doctors.mvp.views.EditProfileView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.DateUtils
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_doctor.*
import java.io.IOException

class EditDoctorActivity : BaseActivity(), EditProfileView {

    private lateinit var mPresenter: EditProfilePresenter

    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var bloodType: String? = null
    private var bitmap: Bitmap? = null
    private var specialityMyanmar: String? = null
    private var gender:String? = ""
    private var mDoctorVO: DoctorVO? = null


    companion object {
        const val PICK_IMAGE_REQUEST = 1000
        const val DOCTOR_VO = "patient_vo"
        fun newInstance(context: Context, patient: String): Intent {
            val intent = Intent(context, EditDoctorActivity::class.java)
            intent.putExtra(DOCTOR_VO, patient)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_doctor)
        setupPresenter()
        setupLayout()
        setupListener()
        init()
    }

    private fun init() {
        spDay.apply {
            val mSpinAdapter = ArrayAdapter(
                this@EditDoctorActivity, android.R.layout.simple_spinner_item,
                DateUtils.day()
            )
            mSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpinAdapter
        }
        spMonth.apply {
            val mSpMonthAdapter = ArrayAdapter(
                this@EditDoctorActivity, android.R.layout.simple_spinner_item,
                DateUtils.Months.values()
            )
            mSpMonthAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpMonthAdapter
        }
        spYear.apply {
            val spYearAdapter = ArrayAdapter(
                this@EditDoctorActivity, android.R.layout.simple_spinner_item,
                DateUtils.year()
            )
            spYearAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = spYearAdapter
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setupListener() {
        btnContinue.setOnClickListener {
            val dateofbirth = "$day  $month $year"
            mDoctorVO?.photo?.toUri()?.let { it1 -> ivPatientProfile.load(it1,R.drawable.d_94_e_xav_xyaa_3_w_ke) }
            mDoctorVO?.name = etUserName.text.toString()
            mDoctorVO?.phone = etPhoneNumber.text.toString()
            mDoctorVO?.dob = dateofbirth
            mDoctorVO?.speciality_myanmar = specialityMyanmar
            mDoctorVO?.address = etAddress.text.toString()
            mDoctorVO?.gender = gender
            mDoctorVO?.experience = et_experience.text.toString()
            mDoctorVO?.biography = etBiography.text.toString()
            bitmap?.let { it1 -> mDoctorVO?.let { dVO ->
                SessionManager.doctor_name = dVO.name
                mPresenter.onTapSave(it1,dVO) }
            }
                ?: kotlin.run {
                    mDoctorVO?.let {
                        SessionManager.doctor_name = it.name
                        mPresenter.onTapSave(it)
                    }
                }

        }
        ivPatientProfile.setOnClickListener {
            mPresenter.onTapUploadPhoto()
        }
    }

    private fun setupLayout() {
        val data = intent.getStringExtra(DOCTOR_VO)
        mDoctorVO = Gson().fromJson(data, DoctorVO::class.java)
        mDoctorVO?.photo?.toUri()?.let { ivPatientProfile.load(it, R.drawable.image_placeholder) }
        etUserName.setText(mDoctorVO?.name)
        etPhoneNumber.setText(mDoctorVO?.phone)
        etAddress.setText(mDoctorVO?.address)
        et_experience.setText(mDoctorVO?.experience)
        etBiography.setText(mDoctorVO?.biography)
        gender = mDoctorVO?.gender

        val dayMonthYear: List<String>? = mDoctorVO?.dob?.split("")

        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
              //  day = dayMonthYear?.get(0).toString()
                day = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spSpeciality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                specialityMyanmar = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.male ->{
                    gender = male.text.toString()
                    return@setOnCheckedChangeListener
                }
                R.id.female ->{
                    gender = female.text.toString()
                    return@setOnCheckedChangeListener
                }
            }
        }
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<EditProfilePresenterImpl, EditProfileView>()
    }

    override fun openGallary() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )

    }

    override fun hideProgressDialog() {
    }

    override fun navigateToProfileScreen() {
      //  startActivity(DoctorProfileActivity.newInstance(this))
        finish()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(this.contentResolver, filePath)

                        bitmap = ImageDecoder.decodeBitmap(source)
                        ivPatientProfile.load(it, R.drawable.image_placeholder)
                        //  mPresenter.onPhotoTaken(bitmap)
                    } else {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        ivPatientProfile.load(it, R.drawable.image_placeholder)
                        //   mPresenter.onPhotoTaken(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}