package com.aungpyaesone.shared.network

import com.aungpyaesone.shared.data.vos.*

interface FirebaseApi {

    fun getAllDoctor(onSuccess: (doctorList: List<DoctorVO>) -> Unit, onFialure: (String) -> Unit)
    fun addDoctor(doctorVO: DoctorVO,onSuccess:()->Unit,onFialure: (String) -> Unit)
    fun addPatient(patientVO: PatientVO,onSuccess: () -> Unit,onFialure: (String) -> Unit)
    fun deleteDoctor(name: String)

    // common
    fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit,onFailure:(String)->Unit)
    fun startConsultation(caseSummary: QuestionAnswerVO,
                          doctorVO: DoctorVO,
                          patientVO: PatientVO,
                          onSuccess: (currentDocumentId:String) -> Unit,
                          onFailure: (String) -> Unit)

    fun getConsultationChat(onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
    fun getAllCheckMessage(documentId: String,onSuccess: (List<ChatMessageVO>) -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(documentId:String,messageVO: ChatMessageVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // for patient
    fun sendBroadCastConsultationRequest(
            speciality:String,
            caseSummary: QuestionAnswerVO,
            patientVO: PatientVO,
            dateTime : String,
            onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun sendDirectRequest(
            caseSummary: QuestionAnswerVO,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit)

    fun checkoutMedicine(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultationDoctor(documentId:String,onSuccess: (List<DoctorVO>) -> Unit,onFailure: (String) -> Unit)


    // for doctor
    fun acceptRequest(doctor:DoctorVO,
                      onSuccess: () -> Unit,
                      onFailure: (String) -> Unit)

    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun preScribeMedicine(medicine:MedicineVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit,onFailure: (String) -> Unit)

}