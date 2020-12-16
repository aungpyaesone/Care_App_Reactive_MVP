package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.AccountPresenter
import com.aungpyaesone.patient.mvp.view.AccountView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class AccountPresenterImpl : AccountPresenter,AbstractBasePresenter<AccountView>() {
    private val mPatientModel = PatientModelImpls
    override fun onTapLogout() {
       SessionManager.logout()
       mView?.logoutView()
    }

    override fun onTapChangePassword() {
      mView?.showStatusDialog()
    }

    override fun onTapEdit() {
        mView?.navigateToEditProfileScreen()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
       mPatientModel.getPatientByEmail(email = SessionManager.patient_email.toString(),onSuccess = {},onError = {})
       mPatientModel.getPatientFromDbByEmail(SessionManager.patient_email.toString()).observe(lifecycleOwner,
           Observer {
               it?.let {
                   mView?.showAccount(it)
               }

           })
    }
}