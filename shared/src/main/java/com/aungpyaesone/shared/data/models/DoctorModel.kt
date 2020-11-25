package com.aungpyaesone.shared.data.models

import com.aungpyaesone.shared.data.vos.MedicineVO

interface DoctorModel {

    // for doctor
    fun acceptRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun preScribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}