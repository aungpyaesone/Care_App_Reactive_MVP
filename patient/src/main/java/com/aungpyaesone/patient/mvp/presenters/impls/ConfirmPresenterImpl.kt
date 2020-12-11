package com.aungpyaesone.patient.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.patient.mvp.view.ConfirmView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.DataVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.NotificationVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.util.*
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ConfirmPresenterImpl : ConfirmPresenter, AbstractBasePresenter<ConfirmView>() {
    private val mCoreModel = CoreModelImpls
    private val mPatientModel: PatientModel = PatientModelImpls

    override fun onTapConfirmStartConsultation(context: Context) {
        val patientVO = SessionManager.get<PatientVO>(sharePreferencePatient)
        val questionAnswerList = SessionManager.getList(sharePreferenceQandA)
        questionAnswerList?.let {
            patientVO?.let { patientVO ->
                mPatientModel.sendBroadCastConsultationRequest(
                        SessionManager.speciality.toString(),
                        it,
                        patientVO,
                        DoctorVO("10000"),
                        System.currentTimeMillis().toString(),
                        onSuccess = {
                            mView?.navigateToHomeScreen()
                        },
                        onFailure = {}
                )
                // update patient to firestore
                // mCoreModel.addPatient(patientVO,onSuccess = {},onFailure = {})
            }
        }
        patientVO?.let {
            mCoreModel.addPatient(it, onSuccess = {}, onFailure = {})
            sendNotification(context,patientVO)
        }

    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }

    private fun sendNotification(context: Context,patientVO: PatientVO) {
        val notificationVO = NotificationVO()
        val dataVO = DataVO()
        when(SessionManager.speciality){
            "dentist" -> {
                notificationVO.to = DENTIST_DOCTOR_NOTI_KEY
                dataVO.name = context.getString(R.string.noti_title)
                dataVO.dob = "${patientVO.name}${context.getString(R.string.noti_body_for_doctor)}"
                dataVO.id = patientVO.id
                notificationVO.data = dataVO
                mPatientModel.sendNotificationToDoctor(notificationVO,onSuccess = {
                    Log.d("success",it.success.toString())
                },onFailure = {
                    Log.d("failure",it)
                })

            }
            "eye" -> {
                notificationVO.to = EYE_DOCTOR_NOTI_KEY
                dataVO.name = patientVO.name
                dataVO.dob = patientVO.dob
                dataVO.id = patientVO.id
                notificationVO.data = dataVO
                mPatientModel.sendNotificationToDoctor(notificationVO,onSuccess = {
                    Log.d("success",it.success.toString())
                },onFailure = {
                    Log.d("failure",it)
                })
            }
            "cardiologist" -> {
                notificationVO.to = CARDIOLOGIST_DOCTOR_NOTI_KEY
                dataVO.name = patientVO.name
                dataVO.dob = patientVO.dob
                dataVO.id = patientVO.id
                notificationVO.data = dataVO
                mPatientModel.sendNotificationToDoctor(notificationVO,onSuccess = {
                    Log.d("success",it.success.toString())
                },onFailure = {
                    Log.d("failure",it)
                })
            }

        }

    }
}