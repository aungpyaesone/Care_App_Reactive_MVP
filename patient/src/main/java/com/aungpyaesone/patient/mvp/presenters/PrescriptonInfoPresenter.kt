package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.PrescriptionInfoView
import com.padc.shared.mvp.presenter.BasePresenter

interface PrescriptonInfoPresenter : BasePresenter<PrescriptionInfoView> {
    fun onUiReadyForPrescription(chatId:String,lifecycleOwner: LifecycleOwner)
}