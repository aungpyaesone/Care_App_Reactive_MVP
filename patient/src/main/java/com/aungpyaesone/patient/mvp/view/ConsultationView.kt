package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.mvp.views.BaseView

interface ConsultationView : BaseView {
    fun showConsultationList(consultationList: List<ConsultationChatVO>)
    fun navigateToChatActivity(consultationChatVO: ConsultationChatVO)
    fun showPrescriptionDialog(consultationChatVO: ConsultationChatVO)
}