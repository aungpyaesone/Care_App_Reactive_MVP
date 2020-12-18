package com.aungpyaesone.patient.activities

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
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.mvp.presenters.ProfilePresenter
import com.aungpyaesone.patient.mvp.presenters.impls.ProfilePresenterImpl
import com.aungpyaesone.patient.mvp.view.ProfileView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.DateUtils
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.btnContinue
import kotlinx.android.synthetic.main.activity_edit_profile.etHeight
import kotlinx.android.synthetic.main.activity_edit_profile.spBloodType
import kotlinx.android.synthetic.main.activity_edit_profile.spDay
import kotlinx.android.synthetic.main.activity_edit_profile.spMonth
import kotlinx.android.synthetic.main.activity_edit_profile.spYear
import java.io.IOException

class EditProfileActivity : BaseActivity(),ProfileView {

    private lateinit var mPresenter : ProfilePresenter

    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var bloodType: String? = null
    private  var bitmap : Bitmap? = null
    private var mPatientVO : PatientVO? = null

    companion object{
        const val PICK_IMAGE_REQUEST = 1000
        const val PATIENT_VO = "patient_vo"
        fun newInstance(context: Context, patient:String) : Intent {
            val intent = Intent(context,EditProfileActivity::class.java)
            intent.putExtra(PATIENT_VO,patient)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setupPresenter()
        setupLayout()
        setupListener()
        init()
    }

    private fun init(){
        spDay.apply {
            val mSpinAdapter =  ArrayAdapter(this@EditProfileActivity,android.R.layout.simple_spinner_item,
                DateUtils.day())
            mSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpinAdapter
        }
        spMonth.apply {
            val mSpMonthAdapter = ArrayAdapter(this@EditProfileActivity,android.R.layout.simple_spinner_item,
                DateUtils.Months.values())
            mSpMonthAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = mSpMonthAdapter
        }
        spYear.apply {
            val spYearAdapter =  ArrayAdapter(this@EditProfileActivity,android.R.layout.simple_spinner_item,
                DateUtils.year())
            spYearAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = spYearAdapter
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupListener() {
        btnContinue.setOnClickListener {
            val dateofbirth ="$day  $month $year"
            mPatientVO?.name = etUserName.text.toString()
            mPatientVO?.phone = etPhoneNumber.text.toString()
            mPatientVO?.dob = dateofbirth
            mPatientVO?.height = etHeight.text.toString() + "ft"
            mPatientVO?.blood_type = bloodType
            mPatientVO?.address = etAddress.text.toString()
            mPatientVO?.allergic_medicine = etComment.text.toString()
            bitmap?.let { it1 -> mPatientVO?.let { pVO -> mPresenter.onTapSave(it1 , pVO) } }

        }
        ivPatientProfile.setOnClickListener{
            mPresenter.onTapUploadPhoto()
        }
    }

    private fun setupLayout() {
        val data = intent.getStringExtra(PATIENT_VO)
        mPatientVO = Gson().fromJson(data,PatientVO::class.java)
        mPatientVO?.photo?.toUri()?.let { ivPatientProfile.load(it,R.drawable.image_placeholder) }
        etUserName.setText(mPatientVO?.name)
        etPhoneNumber.setText(mPatientVO?.phone)
        etHeight.setText(mPatientVO?.height)
        etPhoneNumber.setText(mPatientVO?.phone)
        etComment.setText(mPatientVO?.allergic_medicine)
        etAddress.setText(mPatientVO?.address)


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
        spBloodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bloodType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<ProfilePresenterImpl,ProfileView>()
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

    override fun navigateToProfileScreen() {
        finish()
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
                        ivPatientProfile.load(it,R.drawable.image_placeholder)
                      //  mPresenter.onPhotoTaken(bitmap)
                    } else {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        ivPatientProfile.load(it,R.drawable.image_placeholder)
                     //   mPresenter.onPhotoTaken(bitmap)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    override fun showLoading() {

        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE

    }
}