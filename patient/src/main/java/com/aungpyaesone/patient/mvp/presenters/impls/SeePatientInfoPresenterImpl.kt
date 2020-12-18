package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.SeePatientInfoPresenter
import com.aungpyaesone.patient.mvp.view.SeePatientInfoView
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class SeePatientInfoPresenterImpl : SeePatientInfoPresenter,AbstractBasePresenter<SeePatientInfoView>() {
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}