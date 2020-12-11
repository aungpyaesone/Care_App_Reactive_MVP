package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.BasePresenter

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(token:String, registerVO: DoctorVO, password:String)
}