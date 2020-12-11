package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.RegisterPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.RegisterPresenterImpls
import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() , RegisterView {

    private lateinit var mPresenter : RegisterPresenter
    private var mToken: String? = null
    companion object {
        fun newInstance(context: Context) = Intent(context, RegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupPresenter()
        getDeviceId()
        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        btnRegister.setOnClickListener {
            val doctorVO = DoctorVO()
            doctorVO.email = etEmail.text.toString()
            doctorVO.name = etUserName.text.toString()
            doctorVO.speciality = etSpeciality.text.toString()
            mToken?.let { token ->
                mPresenter.onTapRegister(
                        token,
                        doctorVO,
                        etPassword.text.toString()
                )
            }
        }
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<RegisterPresenterImpls,RegisterView>()
    }

    private fun getDeviceId(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            mToken = it
        }
    }

    override fun navigateToHomeScreen() {
        startActivity(MainActivity.newInstance(this))
    }

    override fun navigateToLoginScreen() {
        startActivity(LoginActivity.newInstance(this))
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }
}