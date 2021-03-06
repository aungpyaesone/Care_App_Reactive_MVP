package com.aungpyaesone.patient.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.LoginView
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context: Context,email: String, password: String,lifecycleOwner: LifecycleOwner)
    fun onTapRegister()

}