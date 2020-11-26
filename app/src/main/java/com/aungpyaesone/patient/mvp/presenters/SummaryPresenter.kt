package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.SummaryView
import com.padc.shared.mvp.presenter.BasePresenter

interface SummaryPresenter : BasePresenter<SummaryView> {
    fun onTapStartConsultation()
}