package com.aungpyaesone.doctors.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.views.LoginView
import com.padc.shared.mvp.presenter.BasePresenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context: Context,email: String, password: String,lifecycleOwner: LifecycleOwner)
    fun onTapRegister()
}