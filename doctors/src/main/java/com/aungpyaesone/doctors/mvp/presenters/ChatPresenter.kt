package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.ChatView
import com.padc.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> {
    fun onTapSendMessage(messageText:String)
    fun onTapGeneralMessageTemplate()
    fun onTapGiveMedicine()

}