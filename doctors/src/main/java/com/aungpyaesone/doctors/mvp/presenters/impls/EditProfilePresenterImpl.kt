package com.aungpyaesone.doctors.mvp.presenters.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.EditProfilePresenter
import com.aungpyaesone.doctors.mvp.views.EditProfileView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class EditProfilePresenterImpl : EditProfilePresenter,AbstractBasePresenter<EditProfileView>() {
    private val mDoctoModel = DoctorModelImpls
    private val mAuthModel = AuthenticationModelImpls
    override fun onTapSave(
        bitmap: Bitmap,
        doctorVO: DoctorVO
    ) {
        mView?.showLoading()
        mDoctoModel.uploadPhotoUrl(bitmap,
            onSuccess = {
                mAuthModel.updateProfile(it,onSuccess = {}, onFailure = {})
                mView?.hideLoading()
                SessionManager.photo = it
                val dvO = DoctorVO(
                    id = SessionManager.user_id.toString(),
                    deviceId = SessionManager.device_id.toString(),
                    name = doctorVO.name,
                    email = SessionManager.doctor_email.toString(),
                    photo = it,
                    experience = doctorVO.experience,
                    degree = doctorVO.degree,
                    biography = doctorVO.biography,
                    dob = doctorVO.dob,
                    phone = doctorVO.phone,
                    gender = doctorVO.gender,
                    speciality = SessionManager.speciality,
                    address = doctorVO.address,
                    speciality_myanmar = doctorVO.speciality_myanmar
                    )
                mDoctoModel.addDoctor(dvO,onSuccess = {}, onFailure = {})
            },
            onFailure = {
                mView?.showErrorMessage(it)
            })
    }

    override fun onTapUploadPhoto() {
        mView?.openGallary()
    }

    override fun onPhotoTaken(bitmap: Bitmap) {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}