package com.aungpyaesone.shared.data.models.impls

import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.vos.MedicineVO

object DoctorModelImpls : DoctorModel {
    override fun acceptRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun finishConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun preScribeMedicine(
        medicine: MedicineVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}
