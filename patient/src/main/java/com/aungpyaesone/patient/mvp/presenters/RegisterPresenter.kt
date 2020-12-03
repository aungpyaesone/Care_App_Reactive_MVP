package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.RegisterView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.BasePresenter

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(registerVO:DoctorVO,password:String)
}