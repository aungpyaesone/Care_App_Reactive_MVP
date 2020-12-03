package com.aungpyaesone.shared.network

import com.aungpyaesone.shared.data.vos.*

interface FirebaseApi {
    //1
    fun addDoctor(doctorVO: DoctorVO,onSuccess:()->Unit,onFialure: (String) -> Unit)
    //2
    fun addPatient(patientVO: PatientVO,onSuccess: () -> Unit,onFialure: (String) -> Unit)

    // common
    //3
    fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit,onFailure:(String)->Unit)

    // 4
    fun startConsultation(caseSummary: List<QuestionAnswerVO>,
                          doctorVO: DoctorVO,
                          patientVO: PatientVO,
                          dateTime: String,
                          onSuccess: (currentDocumentId:String) -> Unit,
                          onFailure: (String) -> Unit)

    // 5
    fun getConsultationChat(patientId: String,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
    // 6
    fun getAllCheckMessage(documentId: String,onSuccess: (List<ChatMessageVO>) -> Unit,onFailure: (String) -> Unit)

    // 7
    fun sendMessage(documentId:String,messageVO: ChatMessageVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // 8
    // getSpecialQuestion
    fun getSpecialQuestionBySpecialities(documentName:String,onSuccess: (List<SpecialQuestionVO>) -> Unit,onFailure: (String) -> Unit)

    // 9
    // for patient
    fun sendBroadCastConsultationRequest(
            speciality:String,
            caseSummary: List<QuestionAnswerVO>,
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

    // 10
    fun getRecentlyConsultationDoctor(documentId:String,onSuccess: (List<DoctorVO>) -> Unit,onFailure: (String) -> Unit)


    // for doctor
    fun acceptRequest(doctor:DoctorVO,
                      onSuccess: () -> Unit,
                      onFailure: (String) -> Unit)

    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // 11
    fun preScribeMedicine(documentId: String,medicine:PrescriptionVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)


    // 12
    fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit,onFailure: (String) -> Unit)

}