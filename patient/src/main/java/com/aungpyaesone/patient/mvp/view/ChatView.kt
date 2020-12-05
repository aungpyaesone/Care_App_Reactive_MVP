package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.mvp.views.BaseView

interface ChatView : BaseView{
    fun showConsultationChat(consultationChatList:List<ConsultationChatVO>)
    fun showAllChatMessage(chatMessageList: List<ChatMessageVO>)
    fun navigateToPrescribeMedicineScreen()
}