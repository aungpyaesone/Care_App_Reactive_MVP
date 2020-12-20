package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.RegisterPresenter
import com.aungpyaesone.patient.mvp.view.RegisterView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpls : RegisterPresenter, AbstractBasePresenter<RegisterView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpls
    private val mCoreModel : CoreModel = CoreModelImpls

    override fun onTapRegister(token: String,registerVO: PatientVO, password: String) {
        if(registerVO.email.isNullOrBlank() || registerVO.name.isNullOrBlank() || password.isBlank()){
            mView?.showErrorMessage("please filled account")
        }
        registerVO.email?.let {email->
        registerVO.name?.let { name ->
                mView?.showLoading()
                mAuthenticationModel.register(email, password, name, onSuccess = {userId ->
                    mView?.hideLoading()
                    registerVO.id = userId
                    registerVO.deviceId = token
                    SessionManager.patient_id = userId
                    SessionManager.patient_email = email
                    SessionManager.patient_device_id = token
                    mCoreModel.addPatient(registerVO,onSuccess = {
                        mView?.hideLoading()
                        mView?.navigateToLoginScreen(userId)
                    },onFailure = {
                        mView?.hideLoading()
                        mView?.showErrorMessage(it)
                    }) },
                    onFailure =
                    {
                    mView?.hideLoading()
                    mView?.showErrorMessage(it)
                })
            }
        }
    }

    override fun onTapLogin() {
        mView?.navigateToHomeScreen()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }


}