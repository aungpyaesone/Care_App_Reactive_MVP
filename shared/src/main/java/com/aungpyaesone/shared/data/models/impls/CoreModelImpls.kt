package com.aungpyaesone.shared.data.models.impls

import android.annotation.SuppressLint
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.FirebaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object CoreModelImpls: CoreModel,BaseModel() {
    val mFirebaseApi: FirebaseApi = CloudFireStoreImpls
    override fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.addDoctor(doctorVO, onSuccess, onFailure)
    }

    override fun saveDoctorToDb(doctorVO: DoctorVO) {
        mTheDB.doctorDao().insertDoctor(doctorVO)
    }

    override fun savePatientToDb(patientVO: PatientVO) {
        TODO("Not yet implemented")
    }

    override fun getSpeciality(
        speciality: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
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
