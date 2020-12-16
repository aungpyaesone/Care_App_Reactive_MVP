package com.aungpyaesone.doctors.mvp.presenters

import android.graphics.Bitmap
import com.aungpyaesone.doctors.mvp.views.EditProfileView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.BasePresenter

interface EditProfilePresenter : BasePresenter<EditProfileView> {
    fun onTapSave(bitmap: Bitmap,doctorVO: DoctorVO)
    fun onTapUploadPhoto()
    fun onPhotoTaken(bitmap: Bitmap)

}