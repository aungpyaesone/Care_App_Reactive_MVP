package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.mvp.views.BaseView

interface EditProfileView : BaseView {
    fun openGallary()
    fun hideProgressDialog()
    fun navigateToProfileScreen()
}