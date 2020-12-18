package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.delegate.AcceptDoctorDelegate
import com.aungpyaesone.patient.delegate.RecentlyDoctorDelegate
import com.aungpyaesone.patient.delegate.SpecialitiesDelegate
import com.aungpyaesone.patient.mvp.view.HomeView
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,SpecialitiesDelegate,AcceptDoctorDelegate,RecentlyDoctorDelegate {
    fun onTapPositiveButton()
    fun onTapNegativeButton()
}