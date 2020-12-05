package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.ChatPresenter
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter
class ChatPresenterImpl :  ChatPresenter,AbstractBasePresenter<ChatView>(){
    private val mCoreModel: CoreModel = CoreModelImpls

    init {
        mCoreModel.getAllConsultationChatFromApi(onSuccess = {},onFailure = {
            mView?.showErrorMessage(it)
        })

    }

    override fun onTapSendMessage(messageVO: ChatMessageVO) {
        mCoreModel.sendMessage("",messageVO,onSuccess = {},onFailure = {})
    }

    override fun onTapStartConsultation() {

    }

    override fun onTapPrescribeMedicine() {
            mView?.navigateToPrescribeMedicineScreen()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mCoreModel.getAllConsultationChatFromDb().observe(lifecycleOwner, Observer {
            mView?.showConsultationChat(it)
        })

        mCoreModel.getAllCheckMessageFromApi("",onSuccess = {},onFailure = {
            mView?.showErrorMessage(it)
        })
        mCoreModel.getAllCheckMessageFromDb().observe(lifecycleOwner, Observer {
            mView?.showAllChatMessage(it)
        })
    }
}