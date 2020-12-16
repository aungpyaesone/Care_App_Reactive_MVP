package com.aungpyaesone.doctors.delegate

import com.aungpyaesone.shared.data.vos.MedicineVO

interface MedicineDelegate {
    fun onTapAddMedicine(medicineVO: MedicineVO)
    fun onTapRemoveMedicine(medicineVO: MedicineVO)
}