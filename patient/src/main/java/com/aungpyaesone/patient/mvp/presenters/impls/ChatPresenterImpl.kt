package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.ChatPresenter
import com.aungpyaesone.patient.mvp.view.ChatView
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl :  ChatPresenter,AbstractBasePresenter<ChatView>(){
    override fun onTapSendMessage(messageText: String) {
        TODO("Not yet implemented")
    }

    override fun onTapStartConsultation() {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}