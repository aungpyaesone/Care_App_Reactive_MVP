package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.network.responses.RegistrationResponse

interface DoctorModel {

    // for doctor
    fun acceptRequest(documentId:String, status:String, consultationRequestVO: ConsultationRequestVO,doctorVO: DoctorVO,onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun preScribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun getDoctorFromDbByEmail(email: String) : LiveData<DoctorVO>
    fun getPatientByEmailFromNetwork(
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

}