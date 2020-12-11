package com.aungpyaesone.shared.data.models.impls

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.FirebaseApi
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.util.API_KEY
import com.aungpyaesone.shared.util.EN_ERROR_MESSAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object PatientModelImpls : PatientModel, BaseModel() {
    private val mFirebaseApi: FirebaseApi = CloudFireStoreImpls
    override fun sendBroadCastConsultationRequest(
            speciality: String,
            caseSummary: List<QuestionAnswerVO>,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendBroadCastConsultationRequest(speciality,
                caseSummary,
                patientVO,
                doctorVO, dateTime, onSuccess, onFailure)
    }

    @SuppressLint("CheckResult")
    override fun sendNotificationToDoctor(notificationVO: NotificationVO, onSuccess: (notiResponse: NotiResponse) -> Unit, onFailure: (String) -> Unit) {
        mApiService.sendFcm(notificationVO)
                .map {
                it
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let { data ->
                        onSuccess(it)
                    }
                }, {
                    onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
                })
    }

    override fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    /***
     * @param documentName for specialities document id
     */
    override fun getSpecialQuestionBySpecialities(
            documentName: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpecialQuestionBySpecialities(documentName, onSuccess = {
            mTheDB.specialQuestionDao().insertSpecialQuestionList(it).dbOperationResult({
                onSuccess()
            }, {
                onFailure(it)
            })
        }, onFailure = {
            onFailure(it)
        })
    }

    override fun getSpecialQuestionBySpecialitiesFromDb(speciality: String): LiveData<List<SpecialQuestionVO>> {
        return mTheDB.specialQuestionDao().getSpecialQuestionById(speciality)
    }

    override fun getPatientByEmail(
            email: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) {
        mFirebaseApi.getPatientByEmail(email,
                onSuccess = {
                    // mTheDB.patientDao().deleteAllPatientData().dbOperationResult({},{})
                    mTheDB.patientDao().insertPatient(it).dbOperationResult({
                        onSuccess()
                    }, {
                        onError(it)
                    })
                }, onFailure = { onError(it) })
    }

    override fun getPatientFromDbByEmail(email: String): LiveData<PatientVO> {
        return mTheDB.patientDao().getAllPatientDataByEmail(email)
    }

    override fun getAllConsultationRequestFromApi(
            id: String,
            onSuccess: (List<ConsultationRequestVO>) -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.observeAcceptDoctorRequest(id, onSuccess = {
            mTheDB.consultationReqDao().deleteAllConsultationRequest().dbOperationResult({}, {})
            mTheDB.consultationReqDao().insertConsultationRequestList(it).dbOperationResult({}, {})
        }, onFailure = {
            onFailure(it)
        })
    }


}