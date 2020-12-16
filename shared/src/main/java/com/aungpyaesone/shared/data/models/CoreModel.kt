package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*

interface CoreModel {
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun addPatient(patientVO: PatientVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    // network function
    fun getSpecialityFromNetWork(onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun getRecentlyConsultedDoctorFromApi(
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun startConsultation(caseSummaryList:List<QuestionAnswerVO>,
                          patientVO: PatientVO,
                          doctorVO: DoctorVO,
                          dateTime:String,
                          onSuccess: (documentId:String) -> Unit,
                          onFailure: (String) -> Unit)
    fun sendMessage(
        documentId: String,
        messageVO: ChatMessageVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    // db function
    fun getSpecialityFromDb(): LiveData<List<SpecialitiesVO>>
    fun getRecentlyConsultedDoctorFromDb(): LiveData<List<RecentDoctorVO>>
    fun saveDoctorToDb(doctorVO: DoctorVO)
    fun savePatientToDb(id: String, patientVO: PatientVO)

    fun getAllConsultationChatFromApi(
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllConsultationChatFromDbById(id: String): LiveData<ConsultationChatVO>

    fun getAllCheckMessageFromApi(
        documentId: String,
        onSuccess: (List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllCheckMessageFromDb(): LiveData<List<ChatMessageVO>>

    /***
     * consultation request for doctor and patient
     */
    fun getAllConsultationRequestFromApi(
        speciality: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllConsultationRequestFromDb(): LiveData<List<ConsultationRequestVO>>
    fun getAllDoctorAcceptConsultationRequestFromDb(): LiveData<List<ConsultationRequestVO>>
    fun getConsultationRequestByIdFromDb(id: String): LiveData<ConsultationRequestVO>

    fun getDoctorBySpecialityFromApi(
        speciality: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getDoctorBySpecialityFromDb(): LiveData<List<DoctorVO>>

    fun getAllConsultationChatFromDb():LiveData<List<ConsultationChatVO>>

    fun updateConsultationChat(
        consultationChatVO: ConsultationChatVO,
        onSuccess: (currentDocumentId:String) -> Unit,
        onFailure: (String) -> Unit)
}