package com.aungpyaesone.shared.network

import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO

interface FirebaseApi {

    fun getAllDoctor(onSuccess: (doctorList: List<DoctorVO>) -> Unit, onFialure: (String) -> Unit)
    fun addDoctor(doctorVO: DoctorVO,onSuccess:()->Unit,onFialure: (String) -> Unit)
    fun addPatient(patientVO: PatientVO,onSuccess: () -> Unit,onFialure: (String) -> Unit)
    fun deleteDoctor(name: String)

    // common
    fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit,onFailure:(String)->Unit)
    fun startConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendMessage(text:String?,image:String?,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun getRecentlyConsultatedDoctor(onSuccess: (doctor:DoctorVO) -> Unit,onFailure: (String) -> Unit)

    // for patient
    fun sendBroadCastConsultationRequest(speciality:String,onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun sendDirectRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun checkoutMedicine(onSuccess: () -> Unit,onFailure: (String) -> Unit)


    // for doctor
    fun acceptRequest(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun finishConsultation(onSuccess: () -> Unit,onFailure: (String) -> Unit)
    fun preScribeMedicine(medicine:MedicineVO,onSuccess: () -> Unit,onFailure: (String) -> Unit)

}