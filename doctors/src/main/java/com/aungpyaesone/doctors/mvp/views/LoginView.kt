package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface LoginView : BaseView {
    fun navigateToRegisterScreen()
    fun navigateToHomeScreen(doctorVO: DoctorVO)
}