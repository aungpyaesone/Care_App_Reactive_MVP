package com.aungpyaesone.shared.network

import android.util.Log
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.convertToGeneralQuestionVO
import com.aungpyaesone.shared.util.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.util.*

object CloudFireStoreImpls : FirebaseApi {
    val db = Firebase.firestore

    override fun addDoctor(doctorVO: DoctorVO, onSuccess: () -> Unit, onFialure: (String) -> Unit) {
        val doctorMap = hashMapOf(
            "id" to doctorVO.id,
            "name" to doctorVO.name,
            "photo" to doctorVO.photo,
            "biography" to doctorVO.biography,
            "degree" to doctorVO.degree,
            "experience" to doctorVO.experience,
            "address" to doctorVO.address,
            "email" to doctorVO.email,
            "speciality" to doctorVO.speciality,
            "deviceId" to doctorVO.deviceId,
            "phone" to doctorVO.phone
        )
        doctorVO.name?.let {
            db.collection("doctors")
                    .document(doctorVO.id)
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
            db.collection(PATIENT)
                    .document(patientVO.id)
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

    override fun getSpeciality(onSuccess: (List<SpecialitiesVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection(SPECIALITIES)
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
          /*.addSnapshotListener { value, error ->
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
            caseSummary: List<QuestionAnswerVO>,
            doctorVO: DoctorVO,
            patientVO: PatientVO,
            dateTime: String,
            onSuccess: (currentDocumentId: String) -> Unit,
            onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID()
        val consultationMap = hashMapOf(
                "id" to uuid.toString(),
                "caseSummary" to caseSummary,
                "doctor" to doctorVO,
                "patient" to patientVO,
                "patient_id" to patientVO.id,
                "dateTime" to dateTime,
                "status" to false
        )

        db.collection(CONSULTATION_CHAT)
                .document(uuid.toString())
                .set(consultationMap)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add doctor")
                    onSuccess(uuid.toString())
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add doctor")
                    onFailure("Failed to add doctor")
                }

        // add recently doctor
        db.collection("$PATIENT/${patientVO.id}/$RECENTLY_DOCTOR")
                .document(doctorVO.id)
                .set(doctorVO)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add recent doctor")
                    onSuccess(uuid.toString())
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add recent doctor")
                    onFailure("Failed to add doctor")
                }

        // add general question
        for (questionAns in caseSummary) {
            db.collection("$PATIENT/${patientVO.id}/$GENERAL_QUESTION")
                    .document(questionAns.id)
                    .set(questionAns)
                    .addOnSuccessListener {
                        Log.d("success", "Successfully add question answer")
                        onSuccess(uuid.toString())
                    }
                    .addOnFailureListener {
                        Log.d("onFailure", "Failed to question answer")
                        onFailure("Failed to add doctor")
                    }
        }
    }

    override fun sendMessage(
            documentId: String,
            messageVO: ChatMessageVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit) {
        val uuid = UUID.randomUUID()
        val messageMap = hashMapOf(
                "id" to uuid.toString(),
                "messageText" to messageVO.messageText,
                "messageImage" to messageVO.messageImage,
                "sentBy" to messageVO.sendBy,
                "sendAt" to messageVO.sendAt

        )
        db.collection("$CONSULTATION_CHAT/${documentId}/$MESSAGE")
                .document(uuid.toString())
                .set(messageMap)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
                }
    }

    override fun getSpecialQuestionBySpecialities(
            documentName: String,
            onSuccess: (List<SpecialQuestionVO>) -> Unit,
            onFailure: (String) -> Unit
    ) {
        db.collection(
                "$SPECIALITIES/${documentName}/$SPECIAL_QUESTION_NODE")
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: "Please check internet connection")
                    } ?: run {
                        val specialQuestionList: MutableList<SpecialQuestionVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val hashmap = document.data
                            hashmap?.put("id", document.id)
                            val Data = Gson().toJson(hashmap)
                            val docData = Gson().fromJson<SpecialQuestionVO>(Data, SpecialQuestionVO::class.java)
                            specialQuestionList.add(docData)
                        }
                        onSuccess(specialQuestionList)
                    }
                }
    }

    override fun getConsultationChat(onSuccess: (List<ConsultationChatVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection(CONSULTATION_CHAT)
               // .whereEqualTo("patient_id", patientId)
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: EN_ERROR_MESSAGE)
                    } ?: run {
                        val consultationChatList: MutableList<ConsultationChatVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val hashmap = document.data
                            hashmap?.put("id", document.id)
                            val Data = Gson().toJson(hashmap)
                            val docData = Gson().fromJson<ConsultationChatVO>(Data, ConsultationChatVO::class.java)
                            consultationChatList.add(docData)
                        }
                        onSuccess(consultationChatList)
                    }
                }
    }

    override fun getAllCheckMessage(documentId: String, onSuccess: (List<ChatMessageVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("$CONSULTATION_CHAT/${documentId}/$MESSAGE")
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: "Please check internet connection")
                    } ?: run {
                        val chatMessageList: MutableList<ChatMessageVO> = arrayListOf()
                        val result = value?.documents ?: arrayListOf()
                        for (document in result) {
                            val hashmap = document.data
                            hashmap?.put("id", document.id)
                            val Data = Gson().toJson(hashmap)
                            val docData = Gson().fromJson<ChatMessageVO>(Data, ChatMessageVO::class.java)
                            chatMessageList.add(docData)
                        }
                        onSuccess(chatMessageList)

                    }
                }
    }
    // send broadcast request
    override fun sendBroadCastConsultationRequest(speciality: String,
                                                  caseSummary: List<QuestionAnswerVO>,
                                                  patientVO: PatientVO,
                                                  doctorVO: DoctorVO,
                                                  dateTime: String,
                                                  onSuccess: () -> Unit,
                                                  onFailure: (String) -> Unit) {
        val uuid = UUID.randomUUID().toString()
        val consultationReqMap = hashMapOf(
                "id" to uuid,
                "case_summary" to caseSummary,
                "patient" to patientVO,
                "doctor" to doctorVO,
                "patient_id" to patientVO.id,
                "speciality" to speciality,
                "date_time" to dateTime,
                "status" to "new"
        )
        Log.d("uuid",uuid)
        db.collection(CONSULTATION_REQUEST)
                .document(uuid)
                .set(consultationReqMap)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add consultation req")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add consultation req")
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

    override fun checkoutMedicine(
        address: String,
        doctorVO: DoctorVO,
        patientVO: PatientVO,
        prescriptionList: List<PrescriptionVO>,
        totalPrice: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

       val deliveryRoutineVO = DeliveryRoutineVO(
           "",DateUtils().getDayAgo(3).toString()
       )
       val uuid = UUID.randomUUID().toString()
       val checkoutMap = hashMapOf(
           "id" to uuid,
           "address" to address,
           "patient" to patientVO,
           "doctor" to doctorVO,
           "delivery_routine" to deliveryRoutineVO,
           "prescription" to prescriptionList,
           "total_price" to totalPrice
       )
        db.collection(CHECKOUT)
            .document(uuid.toString())
            .set(checkoutMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
            }

    }

    override fun getRecentlyConsultationDoctor(documentId: String, onSuccess: (List<RecentDoctorVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection("$PATIENT/${documentId}/$RECENTLY_DOCTOR")
                .get()
                .addOnSuccessListener { result ->
                    val recentlyDoctorList: MutableList<RecentDoctorVO> = arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<RecentDoctorVO>(Data, RecentDoctorVO::class.java)
                        recentlyDoctorList.add(docData)
                    }
                    onSuccess(recentlyDoctorList)

                }.addOnFailureListener {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                }
    }

    override fun acceptRequest(
        documentId: String,
        status: String,
        consultationRequestVO: ConsultationRequestVO,
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultRequestAcceptMap = hashMapOf(
            "id" to consultationRequestVO.id,
            "patient" to consultationRequestVO.patient,
            "status" to status,
            "doctor" to doctorVO,
            "speciality" to doctorVO.speciality,
            "patient_id" to consultationRequestVO.patient?.id,
            "date_time" to consultationRequestVO.date_time,
            "case_summary" to consultationRequestVO.case_summary
        )

        db.collection(CONSULTATION_REQUEST)
            .document(documentId)
            .set(consultRequestAcceptMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
            }

    }

    override fun finishConsultation(
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val finishConsultationMap = hashMapOf(
            "status" to true,
            "id" to documentId
        )
        db.collection(CONSULTATION_CHAT)
            .document(documentId)
            .set(finishConsultationMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
            }
    }

    override fun preScribeMedicine(
            documentId: String,
            medicine: PrescriptionVO,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    ) {

        val uuid = UUID.randomUUID()
        val prescriptionMap = hashMapOf(
                "id" to uuid.toString(),
                "medicine_name" to medicine.medicine_name,
                "routine" to medicine.routineVO,
        )
        db.collection("$CONSULTATION_CHAT/${documentId}/$PRESCRIPTION")
                .document(uuid.toString())
                .set(prescriptionMap)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
                }
    }

    override fun getGeneralQuestion(onSuccess: (List<GeneralQuestionVO>) -> Unit, onFailure: (String) -> Unit) {
        db.collection(GENERAL_QUESTION_TEMPLATE)
                .addSnapshotListener { value, error ->
                    error?.let {
                        onFailure(it.message ?: EN_ERROR_MESSAGE)
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

    override fun getPrescriptionMedicine(
        documentId: String,
        onSuccess: (List<PrescriptionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$CONSULTATION_CHAT/${documentId}/$MESSAGE")
            .get()
            .addOnSuccessListener { result ->
                val prescriptionList: MutableList<PrescriptionVO> = arrayListOf()
                for (document in result) {
                    val hashmap = document.data
                    hashmap?.put("id", document.id)
                    val Data = Gson().toJson(hashmap)
                    val docData = Gson().fromJson<PrescriptionVO>(Data, PrescriptionVO::class.java)
                    prescriptionList.add(docData)
                }
                onSuccess(prescriptionList)

            }.addOnFailureListener {
                onFailure(it.message ?: EN_ERROR_MESSAGE)
            }

    }

    override fun getAllMedicine(
        speciality: String,
        onSuccess: (List<MedicineVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$SPECIALITIES/$speciality/$MEDICINE")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val medicineList: MutableList<MedicineVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<MedicineVO>(Data, MedicineVO::class.java)
                        medicineList.add(docData)
                    }
                    onSuccess(medicineList)
                }
            }


    }

    override fun getConsultationRequest(
        speciality: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_REQUEST)
            .whereEqualTo("speciality",speciality)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val consultationRequest: MutableList<ConsultationRequestVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<ConsultationRequestVO>(Data, ConsultationRequestVO::class.java)
                        consultationRequest.add(docData)
                    }
                    onSuccess(consultationRequest)
                }
            }
    }

    override fun observeAcceptDoctorRequest(
        patientId: String,
        onSuccess: (List<ConsultationRequestVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_REQUEST)
            .whereEqualTo("patient_id",patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val consultationRequest: MutableList<ConsultationRequestVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<ConsultationRequestVO>(Data, ConsultationRequestVO::class.java)
                        consultationRequest.add(docData)
                    }
                    onSuccess(consultationRequest)
                }
            }
    }

    override fun getPatientByEmail(
        email: String,
        onSuccess: (patientVO: PatientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(PATIENT)
            .whereEqualTo("email", email)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<PatientVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<PatientVO>(Data, PatientVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list[0])
                }
            }
    }

    override fun getDoctorByEmail(
        email: String,
        onSuccess: (doctorVO: DoctorVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(DOCTOR)
            .whereEqualTo("email", email)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check connection")
                } ?: run {
                    val list: MutableList<DoctorVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id.toString())
                        val Data = Gson().toJson(hashmap)
                        val docsData = Gson().fromJson<DoctorVO>(Data, DoctorVO::class.java)
                        list.add(docsData)
                    }
                    onSuccess(list[0])
                }
            }
    }

    override fun getDoctorBySpeciality(
        speciality: String,
        onSuccess: (List<DoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(DOCTOR)
            .whereEqualTo("speciality",speciality)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val doctorList: MutableList<DoctorVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<DoctorVO>(Data, DoctorVO::class.java)
                        doctorList.add(docData)
                    }
                    onSuccess(doctorList)
                }
            }
    }
}