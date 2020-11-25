package com.aungpyaesone.shared.data.models

interface PatientModel {
    // for patient
    fun sendBroadCastConsultationRequest(speciality:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendDirectRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun checkoutMedicine(onSuccess: () -> Unit,onFailure: (String) -> Unit)
}