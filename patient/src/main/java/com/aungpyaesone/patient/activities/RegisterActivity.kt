package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.mvp.presenters.RegisterPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.RegisterPresenterImpls
import com.aungpyaesone.patient.mvp.view.RegisterView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(),RegisterView {

    private lateinit var mPresenter : RegisterPresenter
    private var mToken: String? = null
    companion object{
        fun newInstance(context: Context)= Intent(context,RegisterActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setUpPresenter()
        setUpActionListeners()
        getDeviceId()
        mPresenter.onUiReady(this)
    }

    private fun getDeviceId(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
           mToken = it
           Log.d("token",it)
        }
    }

    private fun setUpActionListeners() {
        btnRegister.setOnClickListener {
            val patientVO = PatientVO()
            patientVO.email = etEmail.text.toString()
            patientVO.name = etUserName.text.toString()
            mToken?.let { it1 ->
                mPresenter.onTapRegister(
                    it1,
                    patientVO,
                    etPassword.text.toString()
                )
            }
        }
    }

    private fun setUpPresenter() {
       mPresenter = getPresenter<RegisterPresenterImpls,RegisterView>()
    }

    override fun navigateToHomeScreen() {
    }

    override fun navigateToLoginScreen(userId: String) {
        startActivity(LoginActivity.newInstance(this))
        this.finish()
    }

    override fun showLoading() {
        progressView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressView.visibility = View.GONE
    }
}