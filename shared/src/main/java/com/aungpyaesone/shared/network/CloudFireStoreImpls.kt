package com.aungpyaesone.shared.network

import android.util.Log
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CloudFireStoreImpls : FirebaseApi {
    val db = Firebase.firestore
    override fun getAllDoctor(onSuccess: (doctorList: List<DoctorVO>) -> Unit, onFialure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFialure: (String) -> Unit) {
        val doctorMap = hashMapOf(
                "name" to doctorVO.name,
                "photo" to doctorVO.photo,
                "biography" to doctorVO.biography,
                "degree" to doctorVO.degree,
                "experience" to doctorVO.experience,
                "address" to doctorVO.address,
                "email" to doctorVO.email,
                "speciality" to doctorVO.speciality
        )
        doctorVO.name?.let {
            db.collection("doctors")
                    .document(it)
                    .set(doctorMap)
                    .addOnSuccessListener {
                        Log.d("success","Successfully add doctor")
                        onSuccess()
                    }
                    .addOnFailureListener{
                        Log.d("onFailure","Failed to add doctor")
                        onFialure("Failed to add doctor")
                    }
        }
    }

    override fun addPatient(patientVO: PatientVO, onSuccess: () -> Unit, onFialure: (String) -> Unit) {
        val patientMap = hashMapOf(
                "name" to patientVO.name,
                "photo" to patientVO.photo,
                "address" to patientVO.address,
                "email" to patientVO.email,
                "gender" to patientVO.gender,
                "dob" to patientVO.dob,
                "phone" to patientVO.phone,
                "age" to patientVO.age

        )
        patientVO.name?.let {
            db.collection("patients")
                    .document(it)
                    .set(patientMap)
                    .addOnSuccessListener {
                        Log.d("success","Successfully add patient")
                        onSuccess()
                    }
                    .addOnFailureListener{
                        Log.d("onFailure","Failed to add patient")
                        onFialure("Failed to add patient")
                    }
        }

    }

    override fun deleteDoctor(name: String) {
        TODO("Not yet implemented")
    }

    override fun getSpeciality(speciality: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(text: String?, image: String?, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getRecentlyConsultatedDoctor(onSuccess: (doctor: DoctorVO) -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendBroadCastConsultationRequest(speciality: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendDirectRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun acceptRequest(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun finishConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun preScribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }
}