package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.CreateAccountPresenter
import com.aungpyaesone.doctors.mvp.views.CreateAccountView
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class CreateAccountPresnterImpl : CreateAccountPresenter,
    AbstractBasePresenter<CreateAccountView>() {
    private val mCoreModel = CoreModelImpls
    override fun createAccount(doctorVO: DoctorVO) {
        mView?.showLoading()
        mCoreModel.addDoctor(doctorVO, onSuccess = {
            mView?.navigateToLoginScreen()
            mView?.hideLoading()
        }, onFailure = {
            mView?.showErrorMessage(it)
            mView?.hideLoading()
        })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

}