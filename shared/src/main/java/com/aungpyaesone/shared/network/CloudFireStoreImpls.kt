package com.aungpyaesone.shared.network

import android.util.Log
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.convertToChatMessage
import com.aungpyaesone.shared.extensions.convertToGeneralQuestionVO
import com.aungpyaesone.shared.util.EN_ERROR_MESSAGE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

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
                        Log.d("success", "Successfully add doctor")
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d("onFailure", "Failed to add doctor")
                        onFialure("Failed to add doctor")
                    }
        }
    }

    override fun addPatient(patientVO: PatientVO, onSuccess: () -> Unit, onFialure: (String) -> Unit) {
        val patientMap = hashMapOf(
                "id" to patientVO.id,
                "name" to patientVO.name,
                "photo" to patientVO.photo,
                "dob" to patientVO.dob,
                "blood_type" to patientVO.blood_type,
                "blood_pressure" to patientVO.blood_pressure,
                "email" to patientVO.email,
                "deviceId" to patientVO.deviceId,
                "height" to patientVO.height,
                "weight" to patientVO.weight,
                "allergic_medicine" to patientVO.allergic_medicine,
                "created_date" to patientVO.created_date
        )
        patientVO.name?.let {
            db.collection("patients")
                    .document(it)
                    .set(patientMap)
                    .addOnSuccessListener {
                        Log.d("success", "Successfully add patient")
                        onSuccess()
                    }
                    .addOnFailureListener {
                        Log.d("onFailure", "Failed to add patient")
                        onFialure("Failed to add patient")
                    }
        }

    }

    override fun deleteDoctor(name: String) {
        TODO("Not yet implemented")
    }

    override fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("specialities")
                .get()
                .addOnSuccessListener { result ->
                    val specialitiesList: MutableList<SpecialitiesVO> = arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<SpecialitiesVO>(Data, SpecialitiesVO::class.java)
                        specialitiesList.add(docData)
                    }
                    onSuccess(specialitiesList)

                }.addOnFailureListener {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                }
        /*  .addSnapshotListener { value, error ->
              error?.let {
                  onFailure(it.message ?: "Please check internet connection")
              } ?: run {
                  val specialitesList: MutableList<SpecialitiesVO> = arrayListOf()
                  val result = value?.documents ?: arrayListOf()
                  for (document in result) {
                      val hashmap = document.data
                      hashmap?.put("id", document.id)
                      val Data = Gson().toJson(hashmap)
                      val docData = Gson().fromJson<SpecialitiesVO>(Data, SpecialitiesVO::class.java)
                      specialitesList.add(docData)
                  }
                  onSuccess(specialitesList)
              }
          }*/
    }

    override fun startConsultation(
            caseSummary: QuestionAnswerVO,
            doctorVO: DoctorVO, patientVO: PatientVO,
            onSuccess: (currentDocumentId: String) -> Unit,
            onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(
            documentId: String,
            messageVO: ChatMessageVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit) {
        val messageMap = hashMapOf(
                "messageText" to messageVO.messageText,
                "messageImage" to messageVO.messageImage,
                "sentBy" to messageVO.sendBy,
                "sendAt" to messageVO.sendAt

        )
        db.collection("consultation/${documentId}/message")
                .document()
                .set(messageMap)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.localizedMessage)
                }
    }

    override fun getConsultationChat(onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getAllCheckMessage(documentId: String, onSuccess: (List<ChatMessageVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("consultation_chat/${documentId}/message")
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: "Please check internet connection")
                    } ?: run {
                        val chatMessageList: MutableList<ChatMessageVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data.convertToChatMessage()
                            chatMessageList.add(data)
                        }
                        onSuccess(chatMessageList)
                    }
                }

    }

    // send broadcast request
    override fun sendBroadCastConsultationRequest(speciality: String,
                                                  caseSummary: QuestionAnswerVO,
                                                  patientVO: PatientVO,
                                                  dateTime: String,
                                                  onSuccess: () -> Unit,
                                                  onFailure: (String) -> Unit) {
        val consultationReqMap = hashMapOf(
                "case_summary" to caseSummary,
                "patient" to patientVO,
                "speciality" to speciality,
                "date_time" to dateTime
        )
        db.collection("consultation_request")
                .document(dateTime)
                .set(consultationReqMap)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add doctor")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add doctor")
                    onFailure("Failed to add doctor")
                }
    }

    override fun sendDirectRequest(
            caseSummary: QuestionAnswerVO,
            patientVO: PatientVO,
            doctorVO: DoctorVO,
            dateTime: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit) {

        val directConsultationMap = hashMapOf(
                "case_summary" to caseSummary,
                "patient" to patientVO,
                "doctor" to doctorVO,
                "date_time" to dateTime
        )

    }

    override fun checkoutMedicine(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getRecentlyConsultationDoctor(documentId: String, onSuccess: (List<DoctorVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("patient/${documentId}/recently_doctor")
                .addSnapshotListener{ value, error ->
                    error?.let {
                        onFailure(it.message ?: EN_ERROR_MESSAGE)
                    } ?: run {
                        val recentDoctorList: MutableList<DoctorVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val hashmap = document.data
                            hashmap?.put("id", document.id)
                            val Data = Gson().toJson(hashmap)
                            val docData = Gson().fromJson<DoctorVO>(Data, DoctorVO::class.java)
                            recentDoctorList.add(docData)
                        }
                        onSuccess(recentDoctorList)
                    }
                }
    }

    override fun acceptRequest(doctor: DoctorVO,
                               onSuccess: () -> Unit,
                               onFailure: (String) -> Unit) {

    }

    override fun finishConsultation(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun preScribeMedicine(medicine: MedicineVO, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("general_question_template")
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: "Please check internet connection")
                    } ?: run {
                        val generalQuestionList: MutableList<GeneralQuestionVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val data = document.data.convertToGeneralQuestionVO()
                            generalQuestionList.add(data)
                        }
                        onSuccess(generalQuestionList)
                    }
                }
    }
}