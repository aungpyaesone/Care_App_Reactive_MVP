package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface ChatView : BaseView {
    fun showConsultationChat(consultationChatList:ConsultationChatVO)
    fun showAllChatMessage(chatMessageList: List<ChatMessageVO>)
    fun navigateToPrescribeMedicineScreen()
    fun showPrescriptionList(prescriptionList : List<PrescriptionVO>)
    fun navigateToSeePatientInfoScreen(consultationChatVO: ConsultationChatVO?)

    fun navigateToCheckOutScreen(chatId: String)
}