package com.aungpyaesone.shared.data.models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.network.responses.RegistrationResponse

interface DoctorModel {

    // for doctor
    fun acceptRequest(documentId:String, status:String, consultationRequestVO: ConsultationRequestVO,doctorVO: DoctorVO,onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun finishConsultation(consultationChatVO: ConsultationChatVO,prescrctionList: List<PrescriptionVO>,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun preScribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun getDoctorFromDbByEmail(email: String) : LiveData<DoctorVO>
    fun getDoctorByEmailFromApi(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    fun deleteSkipPatientRequestFromDb(consultId:String)

    fun addRegistrationToken(requestVO: RegistrationAddRequest,onSuccess: (response:RegistrationResponse) -> Unit,onFailure: (String) -> Unit)

    fun sendNotificationToPatient(
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getAllMedicineFromNetwork(documentId:String, onSuccess: (List<MedicineVO>) -> Unit, onFailure: (String) -> Unit)
    fun getAllMedicineFromDb() : LiveData<List<MedicineVO>>

    fun getAllGeneralQuestionFromApi(documentId: String,onSuccess: (List<GeneralQuestionVO>) -> Unit,onFailure: (String) -> Unit)
    fun getAllGeneralQuestionFromDb():LiveData<List<GeneralQuestionVO>>

    fun getPrescriptionFromApi(id: String,onSuccess: (List<PrescriptionVO>) -> Unit,onFailure: (String) -> Unit)
    fun getPrescriptionFromDb():LiveData<List<PrescriptionVO>>

    fun addConsultedPatient(doctorId:String,patientVO: PatientVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getAllConsultedPatientFromApi(documentId: String,onSuccess: (List<ConsultedPatientVO>) -> Unit,onFailure: (String) -> Unit)
    fun getAllConsultedPatientFromDb(): LiveData<List<ConsultedPatientVO>>

    fun uploadPhotoUrl(bitmap: Bitmap, onSuccess: (url:String) -> Unit, onFailure: (String) -> Unit)
    fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}