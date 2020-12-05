package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*

interface CoreModel {
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)


    // network function
    fun getSpecialityFromNetWork(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultedDoctorFromApi(documentId: String,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun startConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(documentId:String,messageVO: ChatMessageVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // db function
    fun getSpecialityFromDb():LiveData<List<SpecialitiesVO>>
    fun getRecentlyConsultedDoctorFromDb():LiveData<List<RecentDoctorVO>>
    fun saveDoctorToDb(doctorVO: DoctorVO)
    fun savePatientToDb(patientVO: PatientVO)

    fun getAllConsultationChatFromApi(onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit)
    fun getAllConsultationChatFromDb():LiveData<List<ConsultationChatVO>>

    fun getAllCheckMessageFromApi(documentId:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getAllCheckMessageFromDb():LiveData<List<ChatMessageVO>>
}