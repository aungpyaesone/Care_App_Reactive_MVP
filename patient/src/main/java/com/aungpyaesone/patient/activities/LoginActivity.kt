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
import com.aungpyaesone.shared.util.sharePreferencePatient
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnRegister
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.progressView
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
        btnLoginWithFacebook.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    override fun navigateToRegisterScreen() {
        startActivity(RegisterActivity.newInstance(this))
        finish()
    }

    override fun navigateToHomeScreen() {
        SessionManager.login_status =true
        startActivity(HomeActivity.newInstance(this))
        finish()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

}