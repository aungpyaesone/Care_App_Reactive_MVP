package com.aungpyaesone.doctors.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.AccountPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.AccountPresenterImpl
import com.aungpyaesone.doctors.mvp.views.AccountView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.extensions.load
import com.google.gson.Gson
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_doctor_profile.*

class DoctorProfileActivity : BaseActivity(),AccountView {

    companion object{
            fun newInstance(context: Context)= Intent(context,DoctorProfileActivity::class.java)
    }
    private lateinit var mPresenter : AccountPresenter
    private var mDoctorVO : DoctorVO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)
        setupPresenter()
        setupListener()
        mPresenter.onUiReady(lifecycleOwner = this)
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<AccountPresenterImpl,AccountView>()
    }

    private fun setupListener() {
        tvLogout.setOnClickListener {
            mPresenter.onTapLogout()
        }
        tvChangePassword.setOnClickListener {
            mPresenter.onTapChangePassword()
        }

        ivEditProfile.setOnClickListener {
            mDoctorVO?.let { it1 -> mPresenter.onTapEdit(it1) }
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun showAccount(doctorVO: DoctorVO) {
        mDoctorVO = doctorVO
        tvDName.text = doctorVO.name
        tvPhone.text = doctorVO.phone
        doctorVO.photo?.toUri()?.let { ivDoctorProfile.load(it, R.drawable.image_placeholder) }
        tvSpeciality.text = doctorVO.speciality_myanmar ?: ""
        tvDob.text = doctorVO.dob ?: ""
        tvExp.text = doctorVO.experience ?: ""
        tvGender.text = doctorVO.gender ?: ""
        tvAddress.text = doctorVO.address ?: ""
        tvBiography.text = doctorVO.biography ?: ""
        tvDegree.text = doctorVO.degree ?: ""
    }

    override fun showStatusDialog() {

    }

    override fun logoutView() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to delete this entry?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    startActivity(LoginActivity.newInstance(this))
                    finishAffinity()
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun navigateToEditProfileScreen(doctorVO: DoctorVO) {
     //   val doctorVO = SessionManager.get<DoctorVO>(sharePreferenceDoctor)
        val data = Gson().toJson(doctorVO)
        startActivity(EditDoctorActivity.newInstance(this,data))
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }
}