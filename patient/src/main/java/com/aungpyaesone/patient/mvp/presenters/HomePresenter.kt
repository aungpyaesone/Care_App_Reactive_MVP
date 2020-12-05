package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.delegate.RecentlyDoctorDelegate
import com.aungpyaesone.patient.delegate.SpecialitiesDelegate
import com.aungpyaesone.patient.mvp.view.HomeView
import com.aungpyaesone.patient.views.view_pods.ConsultationViewPod
import com.padc.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,SpecialitiesDelegate,RecentlyDoctorDelegate,ConsultationViewPod.Delegate {
}