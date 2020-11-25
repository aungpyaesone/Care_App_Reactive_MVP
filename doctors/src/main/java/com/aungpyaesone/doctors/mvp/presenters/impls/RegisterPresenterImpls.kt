package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.RegisterPresenter
import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpls: RegisterPresenter, AbstractBasePresenter<RegisterView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpls
    private val mCoreModel : CoreModel = CoreModelImpls
    override fun onTapRegister(registerVO: DoctorVO, password: String) {
        mView?.showLoading()
        registerVO.email?.let {
            registerVO.name?.let { it1 ->
                mAuthenticationModel.register(it, password, it1, onSuccess = {
                mView?.hideLoading()
                mCoreModel.addDoctor(registerVO,onSuccess = {
                    mCoreModel.saveDoctorToDb(registerVO)
                },onFailure = {})
                }, onFailure = {
                    mView?.showErrorMessage(it)
                })
            }
        }
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }


}