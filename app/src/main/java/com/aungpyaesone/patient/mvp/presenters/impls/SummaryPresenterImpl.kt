package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class SummaryPresenterImpl : SummaryPresenter,AbstractBasePresenter<SummaryView>() {
    override fun onTapStartConsultation() {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}