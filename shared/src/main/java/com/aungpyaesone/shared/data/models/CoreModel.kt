package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.SenderVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO

interface CoreModel {
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)



    // network function
    fun getSpecialityFromNetWork()
    fun getRecentlyConsultedDoctor()

    fun startConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(senderVO: SenderVO?,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultatedDoctor(onSuccess: (doctor:DoctorVO) -> Unit,onFailure: (String) -> Unit)

    // db function
    fun getSpecialityFromDb():LiveData<List<SpecialitiesVO>>
    fun getRecentlyConsultedDoctorFromDb()
    fun saveDoctorToDb(doctorVO: DoctorVO)
    fun savePatientToDb(patientVO: PatientVO)

}