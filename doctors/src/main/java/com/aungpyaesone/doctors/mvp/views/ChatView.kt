package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface ChatView : BaseView {
    fun showConsultationChat(consultationChatList:ConsultationChatVO)
    fun showAllChatMessage(chatMessageList: List<ChatMessageVO>)
    fun navigateToPrescribeMedicineScreen(speciality:String)
    fun navigateToGeneralQuestionTemplate(documentId:String)
    fun navigateToNoteActivity()
    fun showPrescriptionList(prescriptionList : List<PrescriptionVO>)
    fun showPatientInfoDialog()
}