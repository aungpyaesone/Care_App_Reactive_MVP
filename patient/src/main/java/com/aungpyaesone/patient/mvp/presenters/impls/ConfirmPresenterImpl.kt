package com.aungpyaesone.patient.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.patient.mvp.view.ConfirmView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.NotificationVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.util.*
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ConfirmPresenterImpl : ConfirmPresenter, AbstractBasePresenter<ConfirmView>() {
    private val mCoreModel = CoreModelImpls
    private val mPatientModel: PatientModel = PatientModelImpls

    override fun onTapConfirmStartConsultation(
        context: Context
    ) {
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
                    mCoreModel.addPatient(patientVO, onSuccess = {}, onFailure = {})

                    if(SessionManager.flag == true) {
                        val notiData = prepareNotification(context,patientVO.deviceId,patientVO)
                        sendNotification(notiData)
                    }
                    else{
                        val notiData = prepareNotification(context,"/topics/$SessionManager.speciality",patientVO)
                        sendNotification(notiData)
                    }
            }
        }


    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }

    private fun sendNotification(notificationVO: NotificationVO) {

        mPatientModel.sendNotificationToDoctor(notificationVO, onSuccess = {
            Log.d("success", it.success.toString())
        }, onFailure = {
            Log.d("failure", it)
        })

    }
}