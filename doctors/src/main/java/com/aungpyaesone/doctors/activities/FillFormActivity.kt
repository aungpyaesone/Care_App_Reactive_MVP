package com.aungpyaesone.doctors.activities

import android.app.Activity
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
import android.widget.Toast
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.CreateAccountPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.CreateAccountPresnterImpl
import com.aungpyaesone.doctors.mvp.views.CreateAccountView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.DateUtils
import com.aungpyaesone.shared.util.checkMyanToEng
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_doctor.*
import kotlinx.android.synthetic.main.activity_update_doctor_profile.*
import kotlinx.android.synthetic.main.activity_update_doctor_profile.etAddress
import kotlinx.android.synthetic.main.activity_update_doctor_profile.etBiography
import kotlinx.android.synthetic.main.activity_update_doctor_profile.etPhoneNumber
import kotlinx.android.synthetic.main.activity_update_doctor_profile.etUserName
import kotlinx.android.synthetic.main.activity_update_doctor_profile.et_experience
import kotlinx.android.synthetic.main.activity_update_doctor_profile.female
import kotlinx.android.synthetic.main.activity_update_doctor_profile.ivBack
import kotlinx.android.synthetic.main.activity_update_doctor_profile.male
import kotlinx.android.synthetic.main.activity_update_doctor_profile.radioGroup
import kotlinx.android.synthetic.main.activity_update_doctor_profile.spDay
import kotlinx.android.synthetic.main.activity_update_doctor_profile.spMonth
import kotlinx.android.synthetic.main.activity_update_doctor_profile.spSpeciality
import kotlinx.android.synthetic.main.activity_update_doctor_profile.spYear
import java.io.IOException

class FillFormActivity : BaseActivity(),CreateAccountView {

    private lateinit var mPresenter : CreateAccountPresenter
    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var specialityEng: String? = null
    private var bitmap: Bitmap? = null
    private var specialityMyanmar: String? = null
    private var gender:String? = ""
    private var mDoctorVO: DoctorVO? = null
    companion object{
            const val PICK_IMAGE_REQUEST = 1001
            fun newInstance(context: Context)= Intent(context,FillFormActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_doctor_profile)
        setupPresenter()
        setupLayout()
        setupListener()
        init()
        mPresenter.onUiReady(this)
    }

    private fun setupPresenter() {
       mPresenter = getPresenter<CreateAccountPresnterImpl,CreateAccountView>()
    }

    private fun setupLayout() {
        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
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
                specialityEng = checkMyanToEng(parent.getItemAtPosition(position).toString())
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
                    gender = getString(R.string.male)
                    return@setOnCheckedChangeListener
                }
                R.id.female ->{
                    gender = getString(R.string.female)
                    return@setOnCheckedChangeListener
                }
                else ->{
                    gender = getString(R.string.male)
                    return@setOnCheckedChangeListener
                }
            }
        }
    }

    private fun setupListener() {
        btnCreateAccount.setOnClickListener {
            val doctorVO = DoctorVO()
            val dateofbirth = "$day  $month $year"
            if (etUserName.text.isNullOrBlank() || etPhoneNumber.text.isNullOrBlank() ||
                etAddress.text.isNullOrBlank() || et_experience.text.isNullOrBlank() ||
                etBiography.text.isNullOrBlank() || etDegree.text.isNullOrBlank()
            ) {
                showErrorMessage("Please fill form completely")
            } else {
                doctorVO.id = SessionManager.user_id.toString()
                doctorVO.name = etUserName.text.toString()
                doctorVO.phone = etPhoneNumber.text.toString()
                doctorVO.dob = dateofbirth
                doctorVO.speciality_myanmar = specialityMyanmar
                doctorVO.speciality = specialityEng
                doctorVO.address = etAddress.text.toString()
                doctorVO.gender = gender ?: getString(R.string.male)
                doctorVO.experience = et_experience.text.toString() + "yrs"
                doctorVO.biography = etBiography.text.toString()
                doctorVO.degree = etDegree.text.toString()
                doctorVO.email = SessionManager.doctor_email
                doctorVO.deviceId = SessionManager.device_id
                bitmap?.let { bitmap ->
                        SessionManager.doctor_name = doctorVO.name
                        mPresenter.createAccount(
                            bitmap = bitmap,
                            doctorVO = doctorVO
                        )
                } ?: kotlin.run {
                    showErrorMessage("please choose profile photo")
                }
            }
        }

        wtfImage.setOnClickListener {
            openGallary()
        }
    }

    private fun init() {
        spDay.apply {
            val mSpinAdapter = ArrayAdapter(
                this@FillFormActivity, android.R.layout.simple_spinner_item,
                DateUtils.day()
            )
            mSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpinAdapter
        }
        spMonth.apply {
            val mSpMonthAdapter = ArrayAdapter(
                this@FillFormActivity, android.R.layout.simple_spinner_item,
                DateUtils.Months.values()
            )
            mSpMonthAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpMonthAdapter
        }
        spYear.apply {
            val spYearAdapter = ArrayAdapter(
                this@FillFormActivity, android.R.layout.simple_spinner_item,
                DateUtils.year()
            )
            spYearAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = spYearAdapter
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun navigateToLoginScreen() {
       startActivity(LoginActivity.newInstance(this))
    }

    private fun openGallary() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.INVISIBLE
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode ==  PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
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
                            wtfImage.load(it, R.drawable.image_placeholder)
                            //  mPresenter.onPhotoTaken(bitmap)
                        } else {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                applicationContext.contentResolver, filePath
                            )
                            wtfImage.load(it, R.drawable.image_placeholder)
                            //   mPresenter.onPhotoTaken(bitmap)
                        }
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

    }
}