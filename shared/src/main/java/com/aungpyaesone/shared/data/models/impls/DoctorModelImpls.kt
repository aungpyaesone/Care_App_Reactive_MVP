package com.aungpyaesone.shared.data.models.impls

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls.mFirebaseApi
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.network.responses.RegistrationResponse
import com.aungpyaesone.shared.util.EN_ERROR_MESSAGE
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
                onSuccess()
            },
            onFailure = {
                onFailure(it)
            })
    }

    override fun finishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescrctionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.finishConsultation(consultationChatVO,prescrctionList,onSuccess,onFailure)
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

    override fun getDoctorByEmailFromApi(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseModel.getDoctorByEmail(email, onSuccess = {
            mTheDB.doctorDao().deleteAllDoctor()
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
        mTheDB.consultationReqDao().deleteAllConsultationRequestById(consultId)
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

    override fun getAllMedicineFromNetwork(
        documentId: String,
        onSuccess: (List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.getAllMedicine(documentId,onSuccess = {
            mTheDB.medicineDao().deleteAllMedicine()
            mTheDB.medicineDao().insertMedicineList(it).dbOperationResult({},{})
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getAllMedicineFromDb(): LiveData<List<MedicineVO>> {
        return mTheDB.medicineDao().getMedicineList()
    }

    override fun getAllGeneralQuestionFromApi(
        documentId: String,
        onSuccess: (List<GeneralQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.getGeneralQuestion(onSuccess={
            mTheDB.generalQuestionTemplateDao().deleteAllGeneralQuestion()
            mTheDB.generalQuestionTemplateDao().insertGeneralQuestionList(it).dbOperationResult({},{})
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getAllGeneralQuestionFromDb(): LiveData<List<GeneralQuestionVO>> {
        return mTheDB.generalQuestionTemplateDao().getAllGeneralQuestion()
    }

    override fun getPrescriptionFromApi(
        id: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getPrescriptionMedicine(id,onSuccess = {
            mTheDB.prescriptionDao().deletePrescription()
            mTheDB.prescriptionDao().insertPrescriptionVOList(it).dbOperationResult({},{})
        },onFailure = {onFailure(it)})
    }

    override fun getPrescriptionFromDb(): LiveData<List<PrescriptionVO>> {
        return mTheDB.prescriptionDao().getPrescription()
    }

    override fun addConsultedPatient(
        doctorId: String,
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.addConsultedPatient(doctorId,patientVO,onSuccess = {
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getAllConsultedPatientFromApi(
        documentId: String,
        onSuccess: (List<ConsultedPatientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseModel.getAllConsultedPatient(documentId,onSuccess={
            mTheDB.consultedPatientDao().deleteAllConsultedPatientData()
            mTheDB.consultedPatientDao().insertConsultedPatientList(it).dbOperationResult({},{})
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getAllConsultedPatientFromDb(): LiveData<List<ConsultedPatientVO>> {
        return mTheDB.consultedPatientDao().getConsultedPatient()
    }

    override fun uploadPhotoUrl(
        bitmap: Bitmap,
        onSuccess: (url: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadImageToFireStore(bitmap,onSuccess,onFailure)
    }

    override fun addDoctor(
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.updateDoctor(doctorVO,onSuccess,onFailure)
    }


}
