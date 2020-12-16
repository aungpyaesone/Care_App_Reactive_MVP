package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.CreateAccountView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.BasePresenter

interface CreateAccountPresenter : BasePresenter<CreateAccountView> {
    fun createAccount(doctorVO: DoctorVO)
}