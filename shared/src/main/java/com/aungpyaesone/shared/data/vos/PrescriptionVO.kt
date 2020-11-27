package com.aungpyaesone.shared.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class PrescriptionVO(
    var ps_id: String= "",
    var total_price: Int = 0,
    var prescriptionMedicineVO: ArrayList<PrescriptionMedicineVO> = arrayListOf()
)

data class PrescriptionMedicineVO(
    var name: String= "",
    var price: Int = 0,
    var count: Int = 0
)