package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.mvp.presenters.LoginPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.LoginPresenterImpl
import com.aungpyaesone.patient.mvp.view.LoginView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.progressView
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(), LoginView {

    private lateinit var mPresenter: LoginPresenter

    companion object {
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpPresenter()
        setUpActionListeners()
        mPresenter.onUiReady(this)
    }

    override fun onStart() {
        super.onStart()
       // mPresenter.onCheckUserLogin()
    }

    private fun setUpActionListeners() {
        btnLogin.setOnClickListener {
          mPresenter.onTapLogin(this, etEmail.text.toString(), etPassword.text.toString(),this)
        }
        btnRegister.setOnClickListener{
            mPresenter.onTapRegister()
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newInstance(this))
    }

    override fun navigateToHomeScreen(patientVO: PatientVO) {
        startActivity(MainActivity.newInstance(this))
        this.finish()
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }

}