package com.aungpyaesone.shared.data.models.impls

import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.FirebaseApi

object PatientModelImpls : PatientModel,BaseModel(){
    private val mFirebaseApi: FirebaseApi = CloudFireStoreImpls
    override fun sendBroadCastConsultationRequest(
        speciality: String,
        caseSummary: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendBroadCastConsultationRequest(speciality,
        caseSummary,
        patientVO,
        dateTime,onSuccess,onFailure)
    }

    override fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getSpecialQuestionBySpecialities(
        documentName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialQuestionBySpecialities(documentName,onSuccess = {
            mTheDB.specialQuestionDao().insertSpecialQuestionList(it).dbOperationResult({
                onSuccess()
            },{
                onFailure(it)
            })
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getSpecialQuestionBySpecialitiesFromDb(speciality: String): LiveData<List<SpecialQuestionVO>> {
        return mTheDB.specialQuestionDao().getAllSpecialQuestionList()
    }

}