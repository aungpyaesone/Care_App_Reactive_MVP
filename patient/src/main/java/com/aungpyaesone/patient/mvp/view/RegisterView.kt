package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.mvp.views.BaseView

interface RegisterView : BaseView {
    fun navigateToHomeScreen()
    fun navigateToLoginScreen(userId:String)
}