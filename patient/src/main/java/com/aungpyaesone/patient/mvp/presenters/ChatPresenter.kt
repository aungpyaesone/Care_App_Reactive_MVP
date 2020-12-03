package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.ChatView
import com.padc.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> {
    fun onTapSendMessage(messageText:String)
    fun onTapStartConsultation()

}