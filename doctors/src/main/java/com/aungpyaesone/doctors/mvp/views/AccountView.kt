package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.views.BaseView

interface AccountView : BaseView {
    fun showAccount(doctorVO: DoctorVO)
    fun showStatusDialog()
    fun logoutView()
    fun navigateToEditProfileScreen(doctorVO: DoctorVO)
}