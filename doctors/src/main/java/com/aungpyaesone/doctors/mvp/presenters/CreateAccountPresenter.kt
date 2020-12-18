package com.aungpyaesone.doctors.mvp.presenters

import android.graphics.Bitmap
import com.aungpyaesone.doctors.mvp.views.CreateAccountView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface CreateAccountPresenter : BasePresenter<CreateAccountView> {
    fun createAccount(bitmap: Bitmap,doctorVO: DoctorVO)

}