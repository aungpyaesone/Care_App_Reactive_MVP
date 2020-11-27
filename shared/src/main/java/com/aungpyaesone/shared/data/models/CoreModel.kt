package com.aungpyaesone.shared.data.models

import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO

interface CoreModel {
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun saveDoctorToDb(doctorVO: DoctorVO)
    fun savePatientToDb(patientVO: PatientVO)

    // common
    fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit,onFailure:(String)->Unit)
    fun startConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(text:String?,image:String?,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultatedDoctor(onSuccess: (doctor:DoctorVO) -> Unit,onFailure: (String) -> Unit)

}