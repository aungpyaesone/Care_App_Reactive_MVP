package com.aungpyaesone.doctors.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.HomePresenter
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.*
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter,AbstractBasePresenter<HomeView>() {

    private val mDoctorModel = DoctorModelImpls
    private val mCoreModel = CoreModelImpls
    private val chatId: String?  = ""

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

        mDoctorModel.getAllConsultedPatientFromApi(SessionManager.user_id.toString(), onSuccess = {},onFailure = {})
        mDoctorModel.getAllConsultedPatientFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.payConsultedPatientList(it)
            }
        })
        mCoreModel.getAllConsultationChatFromApi(onSuccess = {},onFailure = {})
        mCoreModel.getAllConsultationChatFromDb().observe(lifecycleOwner, Observer {
            mView?.showAcceptRequestList(it)
        })
        mCoreModel.getAllConsultationRequestFromApi(speciality =SessionManager.speciality.toString(), onSuccess = {}, onFailure = {})
        mCoreModel.getAllConsultationRequestFromDb().observe(lifecycleOwner, Observer {
            it?.let { consultRequestVoList ->
                mView?.showConsultationList(consultRequestVoList)
            }
        })

    }

    override fun onTapAcceptButton(
            context: Context,
            documentId: String,
            status: String,
            consultationRequestVO: ConsultationRequestVO,
            doctorVO: DoctorVO
    ) {
        // write consultation chat node and update status on consultation request
        mCoreModel.startConsultation(
        consultationRequestVO.case_summary ?: arrayListOf()
                        , consultationRequestVO.patient ?: PatientVO()
                        , doctorVO,
                        System.currentTimeMillis().toString(),
                        onSuccess = {
                            // for add chat id to consultation request node
                            consultationRequestVO.consultationchat_id = it
                            mDoctorModel.acceptRequest(documentId, "accept", consultationRequestVO,
                                        doctorVO, onSuccess = {
                                    sendNotification(context, consultationRequestVO.patient?.deviceId, doctorVO)
                                }, onFailure = {})

                            consultationRequestVO.patient?.let { it1 -> mDoctorModel.addConsultedPatient(doctorId = SessionManager.user_id.toString(),patientVO = it1,onSuccess = {},
                            onFailure = {}) }
                        },
                        onFailure = {})
        mView?.navigateToConfirmScreen(consultationRequestVO.id)
    }

    override fun onTapSkipButton(consultId:String) {
        mDoctorModel.deleteSkipPatientRequestFromDb(consultId)
    }

    override fun onTapLaterButton(consultId: String) {
        mDoctorModel.deleteSkipPatientRequestFromDb(consultId)
    }

    override fun onTapChooseTimeButton() {
        mView?.showChooseTimeDialog()
    }

    override fun onTapSendTextMessage(consultChatId: String) {
        mView?.navigateToChatScreen(consultChatId)
    }

    override fun onTapMedicineNote(consultationChatVO: ConsultationChatVO) {
        mView?.showPatientInfoDialog(consultationChatVO)
    }

    override fun onTapNote() {

    }

    override fun onTapPrescribe(consultationChatVO: ConsultationChatVO) {
        mView?.showPrescriptionInfoDialog(consultationChatVO)
    }

    private fun sendNotification(context: Context, deviceId: String?, doctorVO: DoctorVO) {
        val notificationVO = NotificationVO()
        val dataVO = DataVO()
        notificationVO.to = deviceId
        dataVO.title = context.getString(R.string.noti_title)
        dataVO.body = "${doctorVO.name}${context.getString(R.string.noti_body_for_patient)}"
        notificationVO.data = dataVO
        mDoctorModel.sendNotificationToPatient(notificationVO, onSuccess = {
            Log.d("onsuccess", it.success.toString())
        }, onFailure = {
            Log.d("onFailure", it)
        })
    }

}