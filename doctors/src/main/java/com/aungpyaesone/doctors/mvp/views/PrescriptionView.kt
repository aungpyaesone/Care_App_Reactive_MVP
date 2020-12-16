package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.MedicineVO
import com.padc.shared.mvp.views.BaseView

interface PrescriptionView :BaseView {
    fun showMedicineList(medicineList : List<MedicineVO>)
    fun showRoutineDialog(medicineVO: MedicineVO)
    fun removeMedicine(medicineVO: MedicineVO)
    fun finishConsultation()
}