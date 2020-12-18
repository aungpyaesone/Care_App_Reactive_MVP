package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.SummaryView
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface SummaryPresenter : BasePresenter<SummaryView> {
    fun onTapStartConsultation()
}