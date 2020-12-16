package com.aungpyaesone.shared.network

import android.graphics.Bitmap
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
    fun getConsultationChat(onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
    fun getConsultationChatWithPatientId(patientId:String,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
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
            doctorVO: DoctorVO,
            dateTime : String,
            onSuccess: () -> Unit, onFailure: (String) -> Unit)

    /***
     * patient observe consultation accept from doctor
     */
    fun observeAcceptDoctorRequest(patientId:String,onSuccess: (List<ConsultationRequestVO>) -> Unit,onFailure: (String) -> Unit)

    fun sendDirectRequest(
            caseSummary: QuestionAnswerVO,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit)

    fun checkoutMedicine(address:String,
                         doctorVO: DoctorVO,
                         patientVO: PatientVO,
                         prescriptionList: List<PrescriptionVO>,
                         totalPrice:Int,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // 10
    fun getRecentlyConsultationDoctor(documentId:String,onSuccess: (List<RecentDoctorVO>) -> Unit,onFailure: (String) -> Unit)


    // for doctor
    fun acceptRequest(documentId: String,
                      status:String,
                      consultationRequestVO: ConsultationRequestVO,
                      doctorVO: DoctorVO,
                      onSuccess: () -> Unit,
                      onFailure: (String) -> Unit)

    fun finishConsultation(consultationChatVO: ConsultationChatVO,prescriptionList: List<PrescriptionVO>,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    // 11
    fun preScribeMedicine(documentId: String,medicine:PrescriptionVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)


    // 12
    fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit,onFailure: (String) -> Unit)

    // 13
    fun getPrescriptionMedicine(documentId: String,onSuccess: (List<PrescriptionVO>) -> Unit,onFailure: (String) -> Unit)

    // 14
    fun getAllMedicine(speciality: String,onSuccess: (List<MedicineVO>) -> Unit,onFailure: (String) -> Unit)

    // 15
    fun getConsultationRequest(speciality: String,onSuccess: (List<ConsultationRequestVO>)-> Unit,onFailure: (String) -> Unit)

    fun getPatientByEmail(email: String,
                          onSuccess: (patientVO:PatientVO) -> Unit,
                          onFailure: (String) -> Unit)
    fun getDoctorByEmail(email: String,
                          onSuccess: (doctorVO:DoctorVO) -> Unit,
                          onFailure: (String) -> Unit)

    fun getDoctorBySpeciality(speciality: String,onSuccess: (List<DoctorVO>) -> Unit,onFailure: (String) -> Unit)

    fun updateConsultationChat(
                          consultationChatVO: ConsultationChatVO,
                          onSuccess: (currentDocumentId:String) -> Unit,
                          onFailure: (String) -> Unit)

    fun addConsultedPatient(doctorId:String,patientVO: PatientVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getAllConsultedPatient(documentId: String,onSuccess: (List<ConsultedPatientVO>) -> Unit,onFailure: (String) -> Unit)
    fun updateConsultationRequestStatus(consultationRequestVO: ConsultationRequestVO,status:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun uploadImageToFireStore(bitmap: Bitmap,onSuccess: (url:String) -> Unit,onFailure: (String) -> Unit)
    fun updatePatientData(patientVO: PatientVO ,onSuccess: () -> Unit,
                          onFailure: (String) -> Unit
    )

    fun updateDoctor(doctorVO: DoctorVO,onSuccess: () -> Unit,
                          onFailure: (String) -> Unit
    )
}