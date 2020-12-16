package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.ConsultationPresenter
import com.aungpyaesone.patient.mvp.view.ConsultationView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ConsultationPresenterImpl : ConsultationPresenter,AbstractBasePresenter<ConsultationView>() {

    private var mPatientModel = PatientModelImpls
    private var mCoreModel = CoreModelImpls

    override fun onReady(id: String, lifecycleOwner: LifecycleOwner) {

    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mPatientModel.getAllConsultationChatFromApiWithPatientId(SessionManager.patient_id.toString(),onSuccess = {},onFailure = {})
        mPatientModel.getAllConsultationFromDb().observe(lifecycleOwner, Observer {
            mView?.showConsultationList(it)
        })
    }

    override fun onTapSendMessage(consultationChatVO: ConsultationChatVO) {
        mView?.navigateToChatActivity(consultationChatVO)
    }

    override fun onTapPrescription(consultationChatVO: ConsultationChatVO) {
        mView?.showPrescriptionDialog(consultationChatVO)
    }
}