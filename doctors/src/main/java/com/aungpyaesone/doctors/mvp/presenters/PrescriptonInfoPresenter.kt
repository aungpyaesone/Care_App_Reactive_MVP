package com.aungpyaesone.doctors.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.views.PrescriptionInfoView
import com.padc.shared.mvp.presenter.BasePresenter

interface PrescriptonInfoPresenter : BasePresenter<PrescriptionInfoView> {
    fun onUiReadyForPrescription(chatId:String,lifecycleOwner: LifecycleOwner)
}