package com.aungpyaesone.shared.network

import android.graphics.Bitmap
import android.util.Log
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.util.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.*

object CloudFireStoreImpls : FirebaseApi {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

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
            "phone" to doctorVO.phone,
            "speciality_myanmar" to doctorVO.speciality_myanmar,
            "dob" to doctorVO.dob,
            "gender" to doctorVO.gender
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

    override fun addPatient(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFialure: (String) -> Unit
    ) {

            db.collection(PATIENT)
                .document(patientVO.id)
                .set(patientVO)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add patient")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add patient")
                    onFialure("Failed to add patient")
                }
        }

    override fun getSpeciality(
        onSuccess: (List<SpecialitiesVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
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
            "dateTime" to dateTime,
            "status" to false,
            "note" to "",
            "patient_id" to patientVO.id,
            "doctor_id" to doctorVO.id
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
        onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID()
        val messageMap = hashMapOf(
            "id" to uuid.toString(),
            "messageText" to messageVO.messageText,
            "messageImage" to messageVO.messageImage,
            "sentBy" to messageVO.sentBy,
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
            "$SPECIALITIES/${documentName}/$SPECIAL_QUESTION_NODE"
        )
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
                        val docData =
                            Gson().fromJson<SpecialQuestionVO>(Data, SpecialQuestionVO::class.java)
                        specialQuestionList.add(docData)
                    }
                    onSuccess(specialQuestionList)
                }
            }
    }

    override fun getConsultationChat(
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
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
                        val docData = Gson().fromJson<ConsultationChatVO>(
                            Data,
                            ConsultationChatVO::class.java
                        )
                        consultationChatList.add(docData)
                    }
                    onSuccess(consultationChatList)
                }
            }
    }

    override fun getConsultationChatWithPatientId(
        patientId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_CHAT)
            .whereEqualTo("patient_id",patientId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check internet connection")
                } ?: run {
                    val consultationChatVOList: MutableList<ConsultationChatVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData =
                            Gson().fromJson<ConsultationChatVO>(
                                Data,
                                ConsultationChatVO::class.java
                            )
                        consultationChatVOList.add(docData)
                    }
                    onSuccess(consultationChatVOList)
                }
            }

    }

    override fun getConsultationChatWithDoctorId(
        doctorId: String,
        onSuccess: (List<ConsultationChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(CONSULTATION_CHAT)
            .whereEqualTo("doctor_id",doctorId)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please check internet connection")
                } ?: run {
                    val consultationChatVOList: MutableList<ConsultationChatVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData =
                            Gson().fromJson<ConsultationChatVO>(
                                Data,
                                ConsultationChatVO::class.java
                            )
                        consultationChatVOList.add(docData)
                    }
                    onSuccess(consultationChatVOList)
                }
            }
    }

    override fun getAllCheckMessage(
        documentId: String,
        onSuccess: (List<ChatMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
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
                        val docData =
                            Gson().fromJson<ChatMessageVO>(Data, ChatMessageVO::class.java)
                        chatMessageList.add(docData)
                    }
                    onSuccess(chatMessageList)

                }
            }
    }

    // send broadcast request
    override fun sendBroadCastConsultationRequest(
        speciality: String,
        caseSummary: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO,
        dateTime: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uuid = UUID.randomUUID().toString()
        val consultationReqMap = hashMapOf(
            "id" to uuid,
            "case_summary" to caseSummary,
            "patient" to patientVO,
            "doctor" to doctorVO,
            "patient_id" to patientVO.id,
            "doctor_id" to doctorVO.id,
            "speciality" to speciality,
            "date_time" to dateTime,
            "status" to "new"
        )
        Log.d("uuid", uuid)
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
        onFailure: (String) -> Unit
    ) {

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
            "", DateUtils().getDayAgo(3).toString()
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

    override fun getRecentlyConsultationDoctor(
        documentId: String,
        onSuccess: (List<RecentDoctorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
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
            "consultationchat_id" to consultationRequestVO.consultationchat_id,
            "patient_id" to consultationRequestVO.patient?.id,
            "doctor_id" to doctorVO.id,
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
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationMap = hashMapOf(
            "id" to consultationChatVO.id,
            "caseSummary" to consultationChatVO.caseSummary,
            "doctor" to consultationChatVO.doctor,
            "patient" to consultationChatVO.patient,
            "patient_id" to consultationChatVO.patient_id,
            "doctor_id" to consultationChatVO.doctor_id,
            "dateTime" to consultationChatVO.dateTime,
            "note" to consultationChatVO.note,
            "status" to true
        )

        db.collection(CONSULTATION_CHAT)
            .document(consultationChatVO.id)
            .set(consultationMap)
            .addOnSuccessListener {
                Log.d("success", "Successfully add doctor")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("onFailure", "Failed to add doctor")
                onFailure("Failed to add doctor")
            }

        for (item in prescriptionList) {
            db.collection("$CONSULTATION_CHAT/${consultationChatVO.id}/$PRESCRIPTION")
                .document(item.id)
                .set(item)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add doctor")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add doctor")
                    onFailure("Failed to add doctor")
                }
        }

        val recentDoctorVOMap = hashMapOf(
            "id" to consultationChatVO?.doctor?.id,
            "name" to consultationChatVO?.doctor?.name,
            "photo" to consultationChatVO.doctor?.photo,
            "biography" to consultationChatVO.doctor?.biography,
            "degree" to consultationChatVO.doctor?.degree,
            "experience" to consultationChatVO.doctor?.experience,
            "address" to consultationChatVO.doctor?.address,
            "speciality" to consultationChatVO.doctor?.speciality,
            "email" to consultationChatVO.doctor?.email,
            "deviceId" to consultationChatVO.doctor?.deviceId,
            "phone" to consultationChatVO.doctor?.phone,
            "speciality_myanmar" to consultationChatVO.doctor?.speciality_myanmar
        )
        // add recently doctor

        consultationChatVO.doctor?.id?.let {
            db.collection("$PATIENT/${consultationChatVO.patient?.id}/$RECENTLY_DOCTOR")
                .document(it)
                .set(recentDoctorVOMap)
                .addOnSuccessListener {
                    Log.d("success", "Successfully add recent doctor")
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.d("onFailure", "Failed to add recent doctor")
                    onFailure("Failed to add doctor")
                }
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

    override fun getGeneralQuestion(
        onSuccess: (List<GeneralQuestionVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(GENERAL_QUESTION_TEMPLATE)
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val generalQuestionList: MutableList<GeneralQuestionVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData =
                            Gson().fromJson<GeneralQuestionVO>(Data, GeneralQuestionVO::class.java)
                        generalQuestionList.add(docData)
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
        db.collection("$CONSULTATION_CHAT/${documentId}/$PRESCRIPTION")
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
            .whereEqualTo("speciality", speciality)
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
                        val docData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
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
            .whereEqualTo("patient_id", patientId)
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
                        val docData = Gson().fromJson<ConsultationRequestVO>(
                            Data,
                            ConsultationRequestVO::class.java
                        )
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
            .whereEqualTo("speciality", speciality)
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

    override fun updateConsultationChat(
        consultationChatVO: ConsultationChatVO,
        onSuccess: (currentDocumentId: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultationMap = hashMapOf(
            "id" to consultationChatVO.id,
            "caseSummary" to consultationChatVO.caseSummary,
            "doctor" to consultationChatVO.doctor,
            "patient" to consultationChatVO.patient,
            "patient_id" to consultationChatVO.patient_id,
            "docotr_id" to consultationChatVO.doctor_id,
            "dateTime" to consultationChatVO.dateTime,
            "note" to consultationChatVO.note,
            "status" to false
        )

        db.collection(CONSULTATION_CHAT)
            .document(consultationChatVO.id)
            .set(consultationMap)
            .addOnSuccessListener {
                Log.d("success", "Successfully add doctor")
                onSuccess(consultationChatVO.id)
            }
            .addOnFailureListener {
                Log.d("onFailure", "Failed to add doctor")
                onFailure("Failed to add doctor")
            }
    }

    override fun addConsultedPatient(
        doctorId: String,
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultedPatientMap = hashMapOf(
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

        db.collection("$DOCTOR/${doctorId}/$CONSULTED_PATIENT")
            .document(patientVO.id)
            .set(consultedPatientMap)
            .addOnSuccessListener {
                Log.d("success", "Successfully add doctor")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("onFailure", "Failed to add doctor")
                onFailure("Failed to add doctor")
            }
    }

    override fun getAllConsultedPatient(
        documentId: String,
        onSuccess: (List<ConsultedPatientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("$DOCTOR/${documentId}/$CONSULTED_PATIENT")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: EN_ERROR_MESSAGE)
                } ?: run {
                    val consultedPatient: MutableList<ConsultedPatientVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result) {
                        val hashmap = document.data
                        hashmap?.put("id", document.id)
                        val Data = Gson().toJson(hashmap)
                        val docData = Gson().fromJson<ConsultedPatientVO>(
                            Data,
                            ConsultedPatientVO::class.java
                        )
                        consultedPatient.add(docData)
                    }
                    onSuccess(consultedPatient)
                }
            }
    }

    override fun updateConsultationRequestStatus(
        consultationRequestVO: ConsultationRequestVO,
        status: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val consultRequestAcceptMap = hashMapOf(
            "id" to consultationRequestVO.id,
            "patient" to consultationRequestVO.patient,
            "status" to status,
            "doctor" to consultationRequestVO.doctor,
            "speciality" to consultationRequestVO.doctor?.speciality,
            "consultationchat_id" to consultationRequestVO.consultationchat_id,
            "patient_id" to consultationRequestVO.patient?.id,
            "date_time" to consultationRequestVO.date_time,
            "case_summary" to consultationRequestVO.case_summary
        )

        db.collection(CONSULTATION_REQUEST)
            .document(consultationRequestVO.id)
            .set(consultRequestAcceptMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
            }
    }

    override fun uploadImageToFireStore(
        bitmap: Bitmap,
        onSuccess: (url: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            onFailure("Update Profile Failed")
        }.addOnSuccessListener {
            Log.d("upload success", "success upload image")
            //  Log.d(ContentValues.TAG, "User profile updated.")
        }
        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            imageUrl?.let { onSuccess(it) }
        }
    }

    override fun updatePatientData(
        patientVO: PatientVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection(PATIENT)
            .document(patientVO.id)
            .set(patientVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Success", "Successfully") }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
                Log.d("Failure", "Failed ") }
    }

    override fun updateDoctor(
        doctorVO: DoctorVO,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(DOCTOR)
            .document(doctorVO.id)
            .set(doctorVO)
            .addOnSuccessListener {
                onSuccess()
                Log.d("add doctor Success", "Successfully") }
            .addOnFailureListener {
                onFailure(it.localizedMessage ?: EN_ERROR_MESSAGE)
                Log.d("Failure", "Failed ") }
    }
}