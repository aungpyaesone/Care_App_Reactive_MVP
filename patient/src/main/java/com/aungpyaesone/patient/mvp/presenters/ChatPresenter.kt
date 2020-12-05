package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.padc.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> {
    fun onTapSendMessage(messageVO: ChatMessageVO)
    fun onTapStartConsultation()
    fun onTapPrescribeMedicine()
}