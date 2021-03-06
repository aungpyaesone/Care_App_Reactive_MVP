package com.aungpyaesone.shared.data.models.impls

import android.util.Log
import androidx.lifecycle.LiveData
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.FirebaseApi

object CoreModelImpls : CoreModel, BaseModel() {
    val mFirebaseApi: FirebaseApi = CloudFireStoreImpls

    override fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.addDoctor(doctorVO, onSuccess, onFailure)
    }

    override fun addPatient(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.addPatient(patientVO,onSuccess,onFailure)
    }

    override fun saveDoctorToDb(doctorVO: DoctorVO) {
        mTheDB.doctorDao().insertDoctor(doctorVO).dbOperationResult({
            Log.d("success", it)
        }, {
            Log.d("failure", it)
        })
    }

    override fun savePatientToDb(
        id: String,
        patientVO: PatientVO
    ) {
            patientVO.id = id
            mTheDB.patientDao().insertPatient(patientVO).dbOperationResult({
            Log.d("success", it)
        }, {
            Log.d("failure", it)
        })
    }
    /***
     *
     */
    override fun getAllConsultationChatFromApi(onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getConsultationChat(onSuccess={
            mTheDB.consultationChatDao().deleteAllConsultationChat()
            mTheDB.consultationChatDao().insertConsultationChatList(it).dbOperationResult(onSuccess = {
                Log.d("insert cc",it)
            },
            onFailure = {
                onFailure(it)
            })
        },onFailure= {
            onFailure(it)
        })
    }

    override fun getAllConsultationChatFromDbById(id: String): LiveData<ConsultationChatVO> {
        return mTheDB.consultationChatDao().getConsultationChatById(id)
    }

    override fun getAllCheckMessageFromApi(documentId: String, onSuccess: (List<ChatMessageVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getAllCheckMessage(documentId,onSuccess = {
        mTheDB.chatMessageDao().deleteAllChatMessage()
        mTheDB.chatMessageDao().insertChatMessageList(it).dbOperationResult(onSuccess = {
            },onFailure = {
                onFailure(it)
            })
            onSuccess(it)
        },onFailure = {
          onFailure(it)
        })
    }

    override fun getAllCheckMessageFromDb(): LiveData<List<ChatMessageVO>> {
        return mTheDB.chatMessageDao().getAllChatMessage()
    }

    override fun getAllConsultationRequestFromApi(
        speciality: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getConsultationRequest(speciality =speciality, onSuccess = {consultationRequestList ->
            mTheDB.consultationReqDao().deleteAllConsultationRequest()
            mTheDB.consultationReqDao().insertConsultationRequestList(consultationRequestList).dbOperationResult({
                Log.d("insert success",it)
            },{
                onFailure(it)
            })

        }, onFailure = {
            onFailure(it)
        })
    }

    override fun getAllConsultationRequestFromDb():LiveData<List<ConsultationRequestVO>>{
        return mTheDB.consultationReqDao().getConsultationRequest()
    }

    override fun getSpecialityFromNetWork(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpeciality(onSuccess = {
            mTheDB.specialitiesDao().insertSpecialitiesList(it).dbOperationResult(onSuccess = {
                Log.d("success",it)

            },onFailure = {
                Log.d("failure",it)
            })
        }, onFailure = {
            onFailure(it)
        })
    }

    /***
     * @param documentId for getting recently consulted doctor from subCollection in patient node
     */
    override fun getRecentlyConsultedDoctorFromApi(documentId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getRecentlyConsultationDoctor(documentId,onSuccess = {
            mTheDB.recentDoctorDao().deleteAllRecentlyDoctor()
            mTheDB.recentDoctorDao().insertRecentDoctorList(it).dbOperationResult(onSuccess = {result ->
                Log.d("success",result)
            },onFailure = {error ->
                Log.d("failed",error)
            })
            onSuccess()
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getSpecialityFromDb(): LiveData<List<SpecialitiesVO>> {
       return mTheDB.specialitiesDao().getSpecialities()
    }

    override fun getRecentlyConsultedDoctorFromDb(): LiveData<List<RecentDoctorVO>> {
        return mTheDB.recentDoctorDao().getRecentDoctor()
    }


    override fun startConsultation(
        caseSummaryList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: (documentId: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.startConsultation(
            caseSummary = caseSummaryList,
            patientVO = patientVO,
            doctorVO = doctorVO,
            dateTime = dateTime,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = {onFailure(it)}
        )
    }

    /***
     * @param documentId for chat document node id
     * @param messageVO for chat message vo
     */
    override fun sendMessage(
            documentId: String,
            messageVO: ChatMessageVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendMessage(documentId,messageVO,onSuccess,onFailure)
    }

    override fun getAllDoctorAcceptConsultationRequestFromDb(): LiveData<List<ConsultationRequestVO>> {
        return mTheDB.consultationReqDao().getDoctorAcceptConsultationRequest()
    }

    override fun getConsultationRequestByIdFromDb(id: String): LiveData<ConsultationRequestVO> {
        return mTheDB.consultationReqDao().getConsultationRequestById(id)
    }

    override fun getDoctorBySpecialityFromApi(
        speciality: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getDoctorBySpeciality(speciality,onSuccess = {
            mTheDB.doctorDao().insertDoctorList(it).dbOperationResult({},{})
        },onFailure = {
            onFailure(it)
        })
    }

    override fun getDoctorBySpecialityFromDb(): LiveData<List<DoctorVO>> {
        return mTheDB.doctorDao().getDoctor()
    }

    override fun getAllConsultationChatFromDb(): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getConsultationChat()
    }

    override fun updateConsultationChat(
        consultationChatVO: ConsultationChatVO,
        onSuccess: (currentDocumentId: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.updateConsultationChat(consultationChatVO,onSuccess,onFailure)
    }

}
