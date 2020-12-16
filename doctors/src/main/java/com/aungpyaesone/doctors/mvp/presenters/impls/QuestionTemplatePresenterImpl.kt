package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.QuestionTemplatePresenter
import com.aungpyaesone.doctors.mvp.views.QuestionTemplateView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.data.vos.SenderVO
import com.aungpyaesone.shared.util.DateUtils
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class QuestionTemplatePresenterImpl : QuestionTemplatePresenter,AbstractBasePresenter<QuestionTemplateView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpls
    private val mCoreModel = CoreModelImpls
    private var mDocumentId: String? = null
    override fun onReady(documentId: String, lifecycleOwner: LifecycleOwner) {
       mDocumentId = documentId
       mDoctorModel.getAllGeneralQuestionFromApi(documentId,onSuccess = {},onFailure = {})
       mDoctorModel.getAllGeneralQuestionFromDb().observe(lifecycleOwner, Observer {
           mView?.showQuestionView(it)
       })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }

    override fun onTapGeneralQuestion(generalQuestionVO: GeneralQuestionVO) {
        val mDoctorVO = SessionManager.get<DoctorVO>(sharePreferenceDoctor)
        val messageVO = ChatMessageVO()
        val senderVO = SenderVO()
        mDocumentId?.let {
            mDoctorVO?.let {
                senderVO.id = it.id
                senderVO.photo = it.photo
                senderVO.name = it.name
                messageVO.messageText = generalQuestionVO.question
                messageVO.sendAt = DateUtils.getDate(System.currentTimeMillis())
                messageVO.sentBy = senderVO
            }
            mCoreModel.sendMessage(it,messageVO,
            onSuccess = {
                mView?.navigateToChatActivity()
            },onFailure = {
                mView?.showErrorMessage(it)
            }) }

    }
}