package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.AccountPresenter
import com.aungpyaesone.doctors.mvp.views.AccountView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class AccountPresenterImpl : AccountPresenter,AbstractBasePresenter<AccountView>() {
    private val mDoctorModel = DoctorModelImpls
    override fun onTapLogout() {
       SessionManager.logout()
       mView?.logoutView()
    }

    override fun onTapChangePassword() {
      mView?.showStatusDialog()
    }

    override fun onTapEdit(doctorVO: DoctorVO) {
        mView?.navigateToEditProfileScreen(doctorVO)
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
       mDoctorModel.getDoctorByEmailFromApi(email = SessionManager.doctor_email.toString(),onSuccess = {},onError = {})
       mDoctorModel.getDoctorFromDbByEmail(SessionManager.doctor_email.toString()).observe(lifecycleOwner,
           Observer {
               it?.let {
                   mView?.showAccount(it)
               }
           })
    }
}