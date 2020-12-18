package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import com.padc.shared.mvp.views.BaseView

interface HomeView : BaseView {
    fun showConsultationList(consultationList:List<ConsultationRequestVO>)
    fun showAcceptRequestList(consultationChat: List<ConsultationChatVO>)
    fun payConsultedPatientList(consultationList: List<ConsultedPatientVO>)
    fun navigateToConfirmScreen(id:String)
    fun navigateToChatScreen(consultChatId:String)
    fun showChooseTimeDialog(consultationRequestVO: ConsultationRequestVO)

    fun showNotesDialog(consultationChatVO: ConsultationChatVO)

    fun showPatientInfoDialog(consultationChatVO: ConsultationChatVO)
    fun showPrescriptionInfoDialog(consultationChatVO: ConsultationChatVO)
}