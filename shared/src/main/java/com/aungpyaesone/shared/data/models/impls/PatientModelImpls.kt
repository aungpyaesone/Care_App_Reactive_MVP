package com.aungpyaesone.shared.data.models.impls

import com.aungpyaesone.shared.data.models.PatientModel

object PatientModelImpls : PatientModel{
    override fun sendBroadCastConsultationRequest(
        speciality: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

}