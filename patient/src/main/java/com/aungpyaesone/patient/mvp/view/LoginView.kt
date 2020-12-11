package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.views.BaseView

interface LoginView : BaseView {
    fun navigateToRegisterScreen()
    fun navigateToHomeScreen(patientVO: PatientVO)
}