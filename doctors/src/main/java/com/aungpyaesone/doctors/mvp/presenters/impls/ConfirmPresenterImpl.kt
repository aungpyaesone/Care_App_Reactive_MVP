package com.aungpyaesone.doctors.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.doctors.mvp.views.ConfirmView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ConfirmPresenterImpl : ConfirmPresenter,AbstractBasePresenter<ConfirmView>() {
    private val mDoctorModel = DoctorModelImpls
    private val mCoreMode = CoreModelImpls
    private var mPatientVO : PatientVO? = null
    private var mCaseSummaryList : ArrayList<QuestionAnswerVO> = arrayListOf()
    override fun onUIReady(id: String, lifecycleOwner: LifecycleOwner) {
        mCoreMode.getConsultationRequestByIdFromDb(id).observe(lifecycleOwner, Observer {
            it?.let {
                mCaseSummaryList.clear()
                mView?.showPatientInfo(consultationRequestVO = it)
                mPatientVO = it.patient
                mCaseSummaryList.addAll(it.case_summary ?: arrayListOf())
            }
        })
    }

    override fun onTapStartConsultation(context: Context) {
        val doctorVO = SessionManager.get<DoctorVO>(sharePreferenceDoctor)
        doctorVO?.let {
            sendNotification(context,mPatientVO?.deviceId,doctorVO)
        }
        mView?.navigateToChatActivity()
    }

    private fun sendNotification(context: Context,deviceId: String?,doctorVO: DoctorVO){
        val notificationVO = NotificationVO()
        val dataVO = DataVO()
        notificationVO.to = deviceId
        dataVO.name = context.getString(R.string.noti_title)
        dataVO.dob = "${doctorVO.name}${context.getString(R.string.noti_body_for_patient)}"
        notificationVO.data = dataVO
        mDoctorModel.sendNotificationToPatient(notificationVO,onSuccess = {
            Log.d("onsuccess",it.success.toString())
        },onFailure = {
            Log.d("onFailure",it)
        })
    }

}