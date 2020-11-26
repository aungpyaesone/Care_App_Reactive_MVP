package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.ChatPresenter
import com.aungpyaesone.doctors.mvp.views.ChatView
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class ChatPresenterImpl : ChatPresenter,AbstractBasePresenter<ChatView>() {

    override fun onTapSendMessage(messageText: String) {
        TODO("Not yet implemented")
    }

    override fun onTapGeneralMessageTemplate() {
        TODO("Not yet implemented")
    }

    override fun onTapGiveMedicine() {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}