package com.aungpyaesone.doctors.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.LoginPresenter
import com.aungpyaesone.doctors.mvp.views.LoginView
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class LoginPresenterImpl : LoginPresenter,AbstractBasePresenter<LoginView>() {

    override fun onTapLogin(context: Context, email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun onTapRegister() {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}