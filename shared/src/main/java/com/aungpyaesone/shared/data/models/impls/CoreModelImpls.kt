package com.aungpyaesone.shared.data.models.impls

import android.util.Log
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.extensions.dbOperationResult
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.FirebaseApi

object CoreModelImpls: CoreModel,BaseModel() {
    val mFirebaseApi: FirebaseApi = CloudFireStoreImpls
    override fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.addDoctor(doctorVO, onSuccess, onFailure)
    }

    override fun saveDoctorToDb(doctorVO: DoctorVO) {
        mTheDB.doctorDao().insertDoctor(doctorVO).dbOperationResult({
            Log.d("success",it)
        },{
            Log.d("failure",it)
        })
    }

    override fun savePatientToDb(patientVO: PatientVO) {
        TODO("Not yet implemented")
    }

    override fun getSpeciality(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getSpeciality(onSuccess,onFailure)
    }

    override fun startConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(
        text: String?,
        image: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getRecentlyConsultatedDoctor(
        onSuccess: (doctor: DoctorVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}
