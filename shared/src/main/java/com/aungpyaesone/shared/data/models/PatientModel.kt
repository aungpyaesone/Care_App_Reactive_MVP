package com.aungpyaesone.shared.data.models

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO

interface PatientModel {
    // for patient
    fun sendBroadCastConsultationRequest(speciality:String,
                                         caseSummary: List<QuestionAnswerVO>,
                                         patientVO: PatientVO,
                                         dateTime : String,
                                         onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun sendDirectRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun checkoutMedicine(onSuccess: () -> Unit,onFailure: (String) -> Unit)

    fun getSpecialQuestionBySpecialities(documentName:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getSpecialQuestionBySpecialitiesFromDb(speciality: String):LiveData<List<SpecialQuestionVO>>
}