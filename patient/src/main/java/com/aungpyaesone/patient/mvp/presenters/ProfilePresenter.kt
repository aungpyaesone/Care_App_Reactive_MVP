package com.aungpyaesone.patient.mvp.presenters

import android.graphics.Bitmap
import com.aungpyaesone.patient.mvp.view.ProfileView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.BasePresenter

interface ProfilePresenter : BasePresenter<ProfileView> {
    fun onTapSave(bitmap: Bitmap,patientVO: PatientVO)
    fun onTapUploadPhoto()
    fun onPhotoTaken(bitmap: Bitmap)

}