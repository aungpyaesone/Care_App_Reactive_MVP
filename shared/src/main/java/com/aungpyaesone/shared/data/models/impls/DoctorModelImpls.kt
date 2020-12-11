package com.aungpyaesone.shared.data.models.impls

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.network.responses.RegistrationResponse
import com.aungpyaesone.shared.util.API_KEY
import com.aungpyaesone.shared.util.EN_ERROR_MESSAGE
import com.aungpyaesone.shared.util.PROJECT_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DoctorModelImpls : DoctorModel, BaseModel() {
    private val mFirebaseModel = CloudFireStoreImpls
    override fun acceptRequest(
        documentId: String,
        status: String,
        consultationRequestVO: ConsultationRequestVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.acceptRequest(
            documentId = documentId,
            status = status,
            consultationRequestVO = consultationRequestVO,
            doctorVO = doctorVO,
            onSuccess = {
            },
            onFailure = {

            })
    }

    override fun finishConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun preScribeMedicine(
        medicine: MedicineVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getDoctorFromDbByEmail(email: String): LiveData<DoctorVO> {
        return mTheDB.doctorDao().getAllDoctorDataByEmail(email)
    }

    override fun getPatientByEmailFromNetwork(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseModel.getDoctorByEmail(email, onSuccess = {
            mTheDB.doctorDao().insertDoctor(it).dbOperationResult({
                onSuccess()
            }, {
                onError(it)
            })
        }, onFailure = {
            onError(it)
        })
    }

    override fun deleteSkipPatientRequestFromDb(consultId: String) {
        mTheDB.consultationReqDao().deleteAllConsultationRequestById(consultId).dbOperationResult({
        }, {})
    }

    @SuppressLint("CheckResult")
    override fun addRegistrationToken(
        requestVO: RegistrationAddRequest,
        onSuccess: (response: RegistrationResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mApiService.addRegistrationToken(requestVO)
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

    @SuppressLint("CheckResult")
    override fun sendNotificationToPatient(
        notificationVO: NotificationVO,
        onSuccess: (notiResponse: NotiResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
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


}
