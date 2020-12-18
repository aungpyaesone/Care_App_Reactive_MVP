package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.ChatPresenter
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl :  ChatPresenter,AbstractBasePresenter<ChatView>(){
    private val mCoreModel: CoreModel = CoreModelImpls
    private val mPatientModel : PatientModel = PatientModelImpls

    override fun onTapSendMessage(
        chatId: String,
        messageVO: ChatMessageVO
    ) {
        mCoreModel.sendMessage(chatId,messageVO,onSuccess = {},onFailure = {})
    }

    override fun onTapStartConsultation() {

    }

    override fun onTapPrescribeMedicine() {
        mView?.navigateToPrescribeMedicineScreen()
    }

    override fun onReady(chatId: String, lifecycleOwner: LifecycleOwner) {
        mCoreModel.getAllConsultationChatFromApi(onSuccess = {},onFailure = {})
        mCoreModel.getAllConsultationChatFromDbById(chatId).observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showConsultationChat(it)
            }
        })

        mCoreModel.getAllCheckMessageFromApi(chatId,onSuccess = {
           // mView?.showAllChatMessage(it)
        },onFailure = {})

        mCoreModel.getAllCheckMessageFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showAllChatMessage(it)
            }
        })

        mPatientModel.getPrescriptionFromApi(chatId,onSuccess = {},onFailure = {mView?.showErrorMessage(it)})

        mPatientModel.getPrescriptionFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showPrescriptionList(it)
            }
        })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }

    override fun onTapPrescription() {

    }

    override fun onTapSeenPatientInfo(consultationChatVO: ConsultationChatVO?) {
        mView?.navigateToSeePatientInfoScreen(consultationChatVO)
    }
}