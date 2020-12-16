package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.delegate.ConsultationDelegate
import com.aungpyaesone.patient.mvp.view.ConsultationView
import com.padc.shared.mvp.presenter.BasePresenter

interface ConsultationPresenter : BasePresenter<ConsultationView>,ConsultationDelegate {

    fun onReady(id:String,lifecycleOwner: LifecycleOwner)
}