package com.aungpyaesone.patient.mvp.presenters

import android.content.Context
import com.aungpyaesone.patient.mvp.view.LoginView
import com.padc.shared.mvp.presenter.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context: Context,email: String, password: String)
    fun onTapRegister()
}