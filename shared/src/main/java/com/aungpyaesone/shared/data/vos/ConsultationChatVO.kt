package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.CaseSummaryTypeConverter
import com.aungpyaesone.shared.persistence.DoctorVOTypeConverter
import com.aungpyaesone.shared.persistence.PatientVOTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties


@Entity(tableName = "consultation_chat")
@IgnoreExtraProperties
@TypeConverters(CaseSummaryTypeConverter::class,PatientVOTypeConverter::class,DoctorVOTypeConverter::class)
data class ConsultationChatVO(
    @PrimaryKey
    var cc_id: String= "",
    var patientVO: PatientVO? = null,
    var doctorVO: DoctorVO? = null,
    var case_summary:  ArrayList<QuestionAnswerVO>? = arrayListOf()
)