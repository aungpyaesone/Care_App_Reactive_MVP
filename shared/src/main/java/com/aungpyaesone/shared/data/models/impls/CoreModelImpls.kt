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

    override fun saveDoctorToDb(doctorVO: DoctorVO) {
        mTheDB.doctorDao().insertDoctor(doctorVO).dbOperationResult({
            Log.d("success", it)
        }, {
            Log.d("failure", it)
        })
    }

    override fun savePatientToDb(patientVO: PatientVO) {
        TODO("Not yet implemented")
    }
    /***
     *
     */
    override fun getAllConsultationChatFromApi(onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getConsultationChat(onSuccess={
            mTheDB.consultationChatDao().insertConsultationChatList(it).dbOperationResult(onSuccess = {
                Log.d("insert cr",it)
            },
            onFailure = {
                onFailure(it)
            })
        },onFailure= {
            onFailure(it)
        })
    }

    override fun getAllConsultationChatFromDb(): LiveData<List<ConsultationChatVO>> {
        return mTheDB.consultationChatDao().getConsultationChat()
    }

    override fun getAllCheckMessageFromApi(documentId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getAllCheckMessage(documentId,onSuccess = {
          mTheDB.chatMessageDao().insertChatMessageList(it).dbOperationResult(onSuccess = {
             onSuccess()
            },onFailure = {
                onFailure(it)
            })
        },onFailure = {
          onFailure(it)
        })
    }

    override fun getAllCheckMessageFromDb(): LiveData<List<ChatMessageVO>> {
        return mTheDB.chatMessageDao().getAllChatMessage()
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


    override fun startConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
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

}
