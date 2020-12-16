package com.aungpyaesone.patient.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.LoginPresenter
import com.aungpyaesone.patient.mvp.view.LoginView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {
    private val mAuthModel  : AuthenticationModel = AuthenticationModelImpls
    private val mPatientModel = PatientModelImpls
    override fun onTapLogin(
        context: Context,
        email: String,
        password: String,
        lifecycleOwner: LifecycleOwner
    ) {
        if(email.isEmpty() || password.isEmpty()){
            mView?.showErrorMessage("Please enter all the fields")
        } else {
            mView?.showLoading()
            mAuthModel.login(email, password, onSuccess = {
                mPatientModel.getPatientByEmail(email,onSuccess = {}, onError = {
                    mView?.showErrorMessage(it)
                    mView?.hideLoading()
                })
                mPatientModel.getPatientFromDbByEmail(email).observe(lifecycleOwner, Observer {
                    it?.let {
                        it.deviceId = SessionManager.patient_device_id
                        mPatientModel.addPatient(it,onSuccess = {},onFailure = {})

                        SessionManager.patient_name = it.name
                        SessionManager.patient_id = it.id
                        SessionManager.patient_dateOfBirth = it.dob
                        SessionManager.comment= it.allergic_medicine
                        SessionManager.patient_email = it.email
                        SessionManager.patient_height = it.height
                        SessionManager.patient_blood_type = it.blood_type
                        SessionManager.weight = it.weight
                        SessionManager.patient_device_id = it.deviceId
                        SessionManager.bloodPressure = it.blood_pressure
                        SessionManager.photo = it.photo
                        SessionManager.created_date = it.created_date
                        SessionManager.address = it.address
                        SessionManager.phone = it.phone

                        Log.d("height",it.height.toString())

                        mView?.hideLoading()
                        mView?.navigateToHomeScreen(it)
                    }

                })
            }, onFailure = {
                mView?.showErrorMessage(it)
                mView?.hideLoading()
            })
        }
    }

    override fun onTapRegister() {
       mView?.navigateToRegisterScreen()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }
}