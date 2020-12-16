package com.aungpyaesone.patient.delegate

import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO

interface ConsultationDelegate {
    fun onTapSendMessage(consultationChatVO: ConsultationChatVO)
    fun onTapPrescription(consultationChatVO: ConsultationChatVO)
}