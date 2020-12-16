package com.aungpyaesone.doctors.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.LoginPresenter
import com.aungpyaesone.doctors.mvp.views.LoginView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class LoginPresenterImpl : LoginPresenter, AbstractBasePresenter<LoginView>() {
    private val mAuthModel = AuthenticationModelImpls
    private val mDoctorModel = DoctorModelImpls
    override fun onTapLogin(
        context: Context,
        email: String,
        password: String,
        lifecycleOwner: LifecycleOwner
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            mView?.showErrorMessage("Please enter all the fields")
        } else {
            mView?.showLoading()
            mAuthModel.login(email, password, onSuccess = {
                mDoctorModel.getDoctorByEmailFromApi(email, onSuccess = {
                    mView?.hideLoading()
                }, onError = {
                    mView?.showErrorMessage(it)
                    mView?.hideLoading()
                })
                mDoctorModel.getDoctorFromDbByEmail(email).observe(lifecycleOwner, Observer {
                    it?.let {
                        SessionManager.user_id = it.id
                        SessionManager.doctor_name = it.name
                        SessionManager.device_id = it.deviceId
                        SessionManager.speciality = it.speciality
                        SessionManager.biography = it.biography
                        SessionManager.photo = it.photo
                        SessionManager.experience = it.experience
                        SessionManager.address = it.address
                        SessionManager.phone = it.phone
                        SessionManager.degree = it.degree
                        SessionManager.doctor_email = it.email
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