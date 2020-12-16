package com.aungpyaesone.doctors.delegate

import com.aungpyaesone.shared.data.vos.ConsultationChatVO

interface AcceptPatientListDelegate {
    fun onTapSendTextMessage(consultChatId:String)
    fun onTapMedicineNote(consultationChatVO: ConsultationChatVO)
    fun onTapNote()
    fun onTapPrescribe(consultationChatVO: ConsultationChatVO)
}