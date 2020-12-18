package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.mvp.views.BaseView

interface RegisterView : BaseView {
    fun navigateToHomeScreen()
    fun navigateToLoginScreen()
    fun navigateToCreateAccountScreen()
}