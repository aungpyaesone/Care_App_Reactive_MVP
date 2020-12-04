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

    override fun getSpecialityFromNetWork() {
        mFirebaseApi.getSpeciality(onSuccess = {
            mTheDB.specialitiesDao().insertSpecialitiesList(it).dbOperationResult(onSuccess = {}, onFailure = {})
        }, onFailure = {
        })
    }

    override fun getRecentlyConsultedDoctorFromApi(documentId: String) {
        mFirebaseApi.getRecentlyConsultationDoctor(documentId,onSuccess = {
            mTheDB.recentDoctorDao().insertRecentDoctorList(it).dbOperationResult(onSuccess = {result ->
                Log.d("success",result)
            },onFailure = {error ->
                Log.d("failed",error)
            })
        },onFailure = {})
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

    override fun sendMessage(
            documentId: String,
            messageVO: ChatMessageVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {
        mFirebaseApi.sendMessage(documentId,messageVO,onSuccess,onFailure)
    }

    override fun getRecentlyConsultatedDoctor(
            onSuccess: (doctor: DoctorVO) -> Unit,
            onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}
