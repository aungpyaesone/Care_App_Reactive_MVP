package com.aungpyaesone.patient.mvp.presenters.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.ProfilePresenter
import com.aungpyaesone.patient.mvp.view.ProfileView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ProfilePresenterImpl : ProfilePresenter,AbstractBasePresenter<ProfileView>() {
    private val mPatientModel = PatientModelImpls
    private val mAuthModel = AuthenticationModelImpls
    override fun onTapSave(bitmap: Bitmap, patientVO: PatientVO) {
        mPatientModel.uploadPhotoUrl(bitmap,
            onSuccess = {
                mAuthModel.updateProfile(it,onSuccess = {}, onFailure = {})
                mView?.hideProgressDialog()

                val patientVo = PatientVO(
                    id= SessionManager.patient_id.toString(),
                    deviceId = SessionManager.patient_device_id.toString(),
                    name = patientVO.name,
                    email = SessionManager.patient_email.toString(),
                    photo = it,
                    blood_type = patientVO.blood_type,
                    blood_pressure =SessionManager.bloodPressure.toString(),
                    dob = patientVO.dob,
                    weight = SessionManager.weight.toString(),
                    height = patientVO.height,
                    allergic_medicine = patientVO.allergic_medicine,
                    phone = patientVO.phone,
                    address = patientVO.address,
                    created_date = SessionManager.created_date
                )
                mPatientModel.addPatient(patientVo,onSuccess = {}, onFailure = {})
            },
            onFailure = {
                mView?.showErrorMessage(it)
            })
    }

    override fun onTapUploadPhoto() {
        mView?.openGallary()
    }

    override fun onPhotoTaken(bitmap: Bitmap) {

    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {}

}