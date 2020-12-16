package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.views.BaseView

interface AccountView : BaseView {
    fun showAccount(patientVO: PatientVO)
    fun showStatusDialog()
    fun logoutView()
    fun navigateToEditProfileScreen()
}