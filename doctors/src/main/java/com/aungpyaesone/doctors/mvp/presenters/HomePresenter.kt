package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.HomeView
import com.padc.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>{
    fun onTapAccept()
}