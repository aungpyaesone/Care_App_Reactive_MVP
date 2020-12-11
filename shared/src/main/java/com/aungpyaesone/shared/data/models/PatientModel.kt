package com.aungpyaesone.shared.data.models

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
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun getPatientFromDbByEmail(email: String) : LiveData<PatientVO>

    fun getAllConsultationRequestFromApi(id:String, onSuccess: (List<ConsultationRequestVO>) -> Unit, onFailure: (String) -> Unit)

}