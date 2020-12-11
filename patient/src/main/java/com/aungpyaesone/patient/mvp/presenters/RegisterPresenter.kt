package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.RegisterView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.BasePresenter

interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(token:String,registerVO:PatientVO,password:String)
}