package com.aungpyaesone.doctors.mvp.presenters.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.CreateAccountPresenter
import com.aungpyaesone.doctors.mvp.views.CreateAccountView
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class CreateAccountPresnterImpl : CreateAccountPresenter,
    AbstractBasePresenter<CreateAccountView>() {
    private val mCoreModel = CoreModelImpls
    private val mDoctorModel = DoctorModelImpls
    override fun createAccount(
        bitmap: Bitmap,
        doctorVO: DoctorVO
    ) {
        mView?.showLoading()
        mDoctorModel.uploadPhotoUrl(bitmap,onSuccess = {
            doctorVO.photo = it
            mView?.hideLoading()
            mCoreModel.addDoctor(doctorVO, onSuccess = {
                mView?.hideLoading()
                mView?.navigateToLoginScreen()
            }, onFailure = {
                mView?.showErrorMessage(it)
                mView?.hideLoading()
            })

        },
        onFailure = {
            mView?.showErrorMessage(it)
            mView?.hideLoading()
        })

    }


    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

}