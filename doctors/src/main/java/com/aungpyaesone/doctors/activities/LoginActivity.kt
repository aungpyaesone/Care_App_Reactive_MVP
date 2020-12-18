package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.LoginPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.LoginPresenterImpl
import com.aungpyaesone.doctors.mvp.views.LoginView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.progressView

class LoginActivity : BaseActivity(),LoginView{

    private lateinit var mPresenter : LoginPresenter
    companion object{
        fun newInstance(context: Context)= Intent(context,LoginActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpPresenter()
        setUpActionListeners()
        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl,LoginView>()
    }

    private fun setUpActionListeners() {
        btnLogin.setOnClickListener {
            mPresenter.onTapLogin(this, etEmail.text.toString(), etPassword.text.toString(),this)
        }
        btnRegister.setOnClickListener{
            mPresenter.onTapRegister()
        }
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newInstance(this))
    }

    override fun navigateToHomeScreen(doctorVO: DoctorVO) {
        SessionManager.login_status =true
        SessionManager.doctor_name = doctorVO.name
        SessionManager.user_id = doctorVO.id
        SessionManager.device_id = doctorVO.deviceId
        SessionManager.doctor_email = doctorVO.email
        SessionManager.speciality = doctorVO.speciality
        SessionManager.put(doctorVO, sharePreferenceDoctor)
        startActivity(MainActivity.newInstance(this))
        finish()
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }

}