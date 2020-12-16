package com.aungpyaesone.patient.delegate

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO

interface AcceptDoctorDelegate {
    fun onTapStartConsultation(consulRequestVO: ConsultationRequestVO)
}