package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.delegate.SpecialitiesDelegate
import com.aungpyaesone.patient.mvp.view.HomeView
import com.padc.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,SpecialitiesDelegate {
}