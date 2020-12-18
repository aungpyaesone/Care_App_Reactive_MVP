package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.ChatPresenter
import com.aungpyaesone.doctors.mvp.views.ChatView
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl : ChatPresenter, AbstractBasePresenter<ChatView>() {

    private val mCoreModel:CoreModel = CoreModelImpls
    private val mDoctorModel: DoctorModel = DoctorModelImpls

    override fun onTapSendMessage(
        documentId: String,
        messageVO: ChatMessageVO
    ) {
        mCoreModel.sendMessage(documentId,messageVO,onSuccess = {},onFailure = {})
    }

    override fun onTapGeneralMessageTemplate(documentId: String) {
        mView?.navigateToGeneralQuestionTemplate(documentId)
    }

    override fun onTapGiveMedicine(speciality: String) {
        mView?.navigateToPrescribeMedicineScreen(speciality)
    }

    override fun onTapNote() {
        mView?.navigateToNoteActivity()
    }

    override fun onReady(documentId: String, lifecycleOwner: LifecycleOwner) {
        mCoreModel.getAllConsultationChatFromApi(onSuccess = {},onFailure = {})
        mCoreModel.getAllConsultationChatFromDbById(documentId).observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showConsultationChat(it)
            }
        })

        mCoreModel.getAllCheckMessageFromApi(documentId,onSuccess = {
           // mView?.showAllChatMessage(it)
        },onFailure = {})
        mCoreModel.getAllCheckMessageFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showAllChatMessage(it)
            }
        })

        mDoctorModel.getPrescriptionFromApi(documentId,onSuccess = {},onFailure = {mView?.showErrorMessage(it)})

        mDoctorModel.getPrescriptionFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showPrescriptionList(it)
            }
        })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {}

    override fun onTapSeenPatientInfo() {
        mView?.showPatientInfoDialog()
    }
}