package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.RegisterView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(token:String,registerVO:PatientVO,password:String)
    fun onTapLogin()
}