package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.SummaryView
import com.padc.shared.mvp.presenter.BasePresenter

interface SummaryPresenter : BasePresenter<SummaryView> {
    fun onTapStartConsultation()
}