package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*

interface CoreModel {
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)



    // network function
    fun getSpecialityFromNetWork()
    fun getRecentlyConsultedDoctorFromApi(documentId: String)

    fun startConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(documentId:String,messageVO: ChatMessageVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultatedDoctor(onSuccess: (doctor:DoctorVO) -> Unit,onFailure: (String) -> Unit)

    // db function
    fun getSpecialityFromDb():LiveData<List<SpecialitiesVO>>
    fun getRecentlyConsultedDoctorFromDb():LiveData<List<RecentDoctorVO>>
    fun saveDoctorToDb(doctorVO: DoctorVO)
    fun savePatientToDb(patientVO: PatientVO)

}