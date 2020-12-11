package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.RegisterPresenter
import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpls: RegisterPresenter, AbstractBasePresenter<RegisterView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpls
    private val mCoreModel : CoreModel = CoreModelImpls

    override fun onTapRegister(token: String, registerVO: DoctorVO, password: String) {
        if(registerVO.email.isNullOrBlank() || registerVO.name.isNullOrBlank() || password.isBlank()){
            mView?.showErrorMessage("please filled account")
        }
        registerVO.email?.let {email->
            registerVO.name?.let { name ->
                mView?.showLoading()
                mAuthenticationModel.register(email, password, name, onSuccess = {userId ->
                    mView?.hideLoading()
                    registerVO.id = userId
                    registerVO.deviceId = SessionManager.device_id
                    mCoreModel.addDoctor(registerVO,onSuccess = {
                        mView?.navigateToLoginScreen()
                    },onFailure = {
                        mView?.showErrorMessage(it)
                        mView?.hideLoading()
                    }) }, onFailure = {
                    mView?.showErrorMessage(it)
                    mView?.hideLoading()
                })
            }
        }
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
     //  onTapRegister(doctorVO,"123456")
    }
}