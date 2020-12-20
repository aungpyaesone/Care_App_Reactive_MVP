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
import com.aungpyaesone.shared.util.prepareNotification
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class HomePresenterImpl : HomePresenter, AbstractBasePresenter<HomeView>() {

    private val mDoctorModel = DoctorModelImpls
    private val mCoreModel = CoreModelImpls
    private val chatId: String? = ""
    override fun onTapPostponeTime(
        context: Context,
        str: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        val doctor = SessionManager.get<DoctorVO>(sharePreferenceDoctor)
        doctor?.let {
            mDoctorModel.acceptRequest(consultationRequestVO.id,
                "postpone $str",
                consultationRequestVO,
                it,
                onSuccess = {
                },
                onFailure = {
                })
        }
        val notiObj = prepareNotification(
            context,
            consultationRequestVO.patient?.deviceId,
            consultationRequestVO.doctor,
            ""
        )
        mDoctorModel.sendNotificationToPatient(notiObj, onSuccess = {}, onFailure = {})
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

        mDoctorModel.getAllConsultedPatientFromApi(
            SessionManager.user_id.toString(),
            onSuccess = {},
            onFailure = {})
        mDoctorModel.getAllConsultedPatientFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.payConsultedPatientList(it)
            }
        })

        mDoctorModel.getAllConsultationChat(onSuccess = {
        }, onFailure = {})
        mDoctorModel.getAllConsultationChatByDoctorId(SessionManager.user_id.toString()).observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showAcceptRequestList(it.asReversed())
            }
        })
        mCoreModel.getAllConsultationRequestFromApi(
            speciality = SessionManager.speciality.toString(),
            onSuccess = {},
            onFailure = {})
        mCoreModel.getAllConsultationRequestFromDb().observe(lifecycleOwner, Observer {
            it?.let { consultRequestVoList ->
                mView?.showConsultationList(consultRequestVoList)
            }
        })

        mDoctorModel.getDoctorByEmailFromApi(SessionManager.doctor_email.toString(), onSuccess = {
            mView?.hideLoading()
            it.deviceId = SessionManager.device_id
            mDoctorModel.addDoctor(it,onSuccess = {},onFailure = {})
            SessionManager.user_id = it.id
            SessionManager.doctor_name = it.name
            SessionManager.device_id = it.deviceId
            SessionManager.speciality = it.speciality
            SessionManager.biography = it.biography
            SessionManager.photo = it.photo
            SessionManager.experience = it.experience
            SessionManager.address = it.address
            SessionManager.phone = it.phone
            SessionManager.degree = it.degree
            SessionManager.doctor_email = it.email
            SessionManager.put(it, sharePreferenceDoctor)
        }, onError = {
            mView?.showErrorMessage(it)
            mView?.hideLoading()
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
                        //  prepareNotification(context,consultationRequestVO.patient?.deviceId,doctorVO,"")
                        val noti = prepareNotification(
                            context,
                            consultationRequestVO.patient?.deviceId,
                            doctorVO,
                            ""
                        )
                        mDoctorModel.sendNotificationToPatient(noti, onSuccess = {
                            Log.d("onsuccess", it.success.toString())
                        }, onFailure = {
                            Log.d("onFailure", it)
                        })

                    }, onFailure = {})

                consultationRequestVO.patient?.let { it1 ->
                    mDoctorModel.addConsultedPatient(doctorId = SessionManager.user_id.toString(),
                        patientVO = it1,
                        onSuccess = {},
                        onFailure = {})
                }
            },
            onFailure = {})
        mView?.navigateToConfirmScreen(consultationRequestVO.id)
    }

    override fun onTapSkipButton(consultId: String) {
        mDoctorModel.deleteSkipPatientRequestFromDb(consultId)
    }

    override fun onTapLaterButton(consultId: String) {
        mDoctorModel.deleteSkipPatientRequestFromDb(consultId)
    }

    override fun onTapChooseTimeButton(consultationRequestVO: ConsultationRequestVO) {
        mView?.showChooseTimeDialog(consultationRequestVO)
    }

    override fun onTapSendTextMessage(consultChatId: String) {
        mView?.navigateToChatScreen(consultChatId)
    }

    override fun onTapMedicineNote(consultationChatVO: ConsultationChatVO) {
        mView?.showPatientInfoDialog(consultationChatVO)
    }

    override fun onTapNote(consultationChatVO: ConsultationChatVO) {
        mView?.showNotesDialog(consultationChatVO)
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

    }

}