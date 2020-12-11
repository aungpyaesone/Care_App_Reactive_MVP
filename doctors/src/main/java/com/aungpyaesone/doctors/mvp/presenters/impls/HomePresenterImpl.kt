package com.aungpyaesone.doctors.mvp.presenters.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.HomePresenter
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.RegistrationAddRequest
import com.aungpyaesone.shared.util.*
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter,AbstractBasePresenter<HomeView>() {

    private val mDoctorModel = DoctorModelImpls
    private val mCoreModel = CoreModelImpls
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mCoreModel.getAllConsultationRequestFromApi(speciality =SessionManager.speciality.toString(), onSuccess = {}, onFailure = {})
        mCoreModel.getAllConsultationRequestFromDb().observe(lifecycleOwner, Observer {
            it?.let { it ->
                mView?.showConsultationList(it)
            }
        })
        mCoreModel.getAllDoctorAcceptConsultationRequestFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showAcceptRequestList(it)
            }
        })

        sendRegistrationToken()

    }

    override fun onTapAcceptButton(
            documentId: String,
            status: String,
            consultationRequestVO: ConsultationRequestVO,
            doctorVO: DoctorVO
    ) {
        mDoctorModel.acceptRequest(documentId,status,consultationRequestVO,doctorVO,onSuccess = {},onFailure = {})
        mView?.navigateToConfirmScreen(consultationRequestVO.id)
    }

    override fun onTapSkipButton(consultId:String) {
        mDoctorModel.deleteSkipPatientRequestFromDb(consultId)
    }

    override fun onTapLaterButton() {

    }

    override fun onTapChooseTimeButton() {

    }

    override fun onTapSendTextMessage() {

    }

    override fun onTapMedicineNote() {

    }

    override fun onTapNote() {

    }

    override fun onTapPrescribe() {

    }

    private fun sendRegistrationToken(){
        val registrionAddRequestVO = RegistrationAddRequest()
        when(SessionManager.speciality){
            "dentist" -> {
                registrionAddRequestVO.operation = OPERATION
                registrionAddRequestVO.notification_key_name = DENTIST_DOCTOR_NOTI_KEY_NAME
                registrionAddRequestVO.notification_key = DENTIST_DOCTOR_NOTI_KEY
                registrionAddRequestVO.registration_ids = arrayListOf(SessionManager.device_id.toString())
                mDoctorModel.addRegistrationToken(requestVO = registrionAddRequestVO,onSuccess = {
                    Log.d("add token success",it.notification_key.toString())
                },onFailure = {})
            }
            "eye" -> {
                registrionAddRequestVO.operation = OPERATION
                registrionAddRequestVO.notification_key_name = EYE_DOCTOR_NOTI_KEY_NAME
                registrionAddRequestVO.notification_key = EYE_DOCTOR_NOTI_KEY
                registrionAddRequestVO.registration_ids = arrayListOf(SessionManager.device_id.toString())
                mDoctorModel.addRegistrationToken(requestVO = registrionAddRequestVO,onSuccess = {
                    Log.d("add token success",it.notification_key.toString())
                },onFailure = {})
            }
            "cardiologist" -> {
                registrionAddRequestVO.operation = OPERATION
                registrionAddRequestVO.notification_key_name = CARDIOLOGIST_DOCTOR_NOTI_KEY_NAME
                registrionAddRequestVO.notification_key = CARDIOLOGIST_DOCTOR_NOTI_KEY
                registrionAddRequestVO.registration_ids = arrayListOf(SessionManager.device_id.toString())
                mDoctorModel.addRegistrationToken(requestVO = registrionAddRequestVO,onSuccess = {
                    Log.d("add token success",it.notification_key.toString())
                },onFailure = {})
            }
        }

    }

}