package com.aungpyaesone.shared.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.network.responses.NotiResponse

interface PatientModel {
    // for patient
    fun sendBroadCastConsultationRequest(speciality:String,
                                         caseSummary: List<QuestionAnswerVO>,
                                         patientVO: PatientVO,
                                         doctorVO: DoctorVO,
                                         dateTime : String,
                                         onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun sendNotificationToDoctor(notificationVO: NotificationVO,onSuccess: (notiResponse:NotiResponse) -> Unit,onFailure: (String) -> Unit)
    fun sendDirectRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun checkoutMedicine(onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun getSpecialQuestionBySpecialities(documentName:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getSpecialQuestionBySpecialitiesFromDb(speciality: String):LiveData<List<SpecialQuestionVO>>

    fun getPatientByEmail(
        email: String,
        onSuccess: (patientVO:PatientVO) -> Unit,
        onError: (String) -> Unit
    )

    fun getPatientFromDbByEmail(email: String) : LiveData<PatientVO>

    fun getAllConsultationRequestFromApi(id:String, onSuccess: (List<ConsultationRequestVO>) -> Unit, onFailure: (String) -> Unit)

    fun getPrescriptionFromApi(id: String,onSuccess: (List<PrescriptionVO>) -> Unit,onFailure: (String) -> Unit)
    fun getPrescriptionFromDb():LiveData<List<PrescriptionVO>>

    fun updateConsultationRequestStatus (consultationRequestVO: ConsultationRequestVO,status:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun uploadPhotoUrl(bitmap:Bitmap,onSuccess: (url:String) -> Unit,onFailure: (String) -> Unit)
    fun addPatient(patientVO: PatientVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getAllConsultationChatFromApiWithPatientId(patientId:String,onSuccess: (List<ConsultationChatVO>) -> Unit,onFailure: (String) -> Unit)
    fun getAllConsultationByPatientIdFromDb(patientId:String) : LiveData<List<ConsultationChatVO>>
}