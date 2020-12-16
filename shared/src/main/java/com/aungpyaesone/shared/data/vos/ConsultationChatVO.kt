package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.converters.CaseSummaryTypeConverter
import com.aungpyaesone.shared.persistence.converters.DoctorVOTypeConverter
import com.aungpyaesone.shared.persistence.converters.PatientVOTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties


@Entity(tableName = "consultation_chat")
@IgnoreExtraProperties
@TypeConverters(CaseSummaryTypeConverter::class, PatientVOTypeConverter::class, DoctorVOTypeConverter::class)
data class ConsultationChatVO(
    @PrimaryKey
    var id: String= "",
    var patient: PatientVO? = null,
    var doctor: DoctorVO? = null,
    var caseSummary:  ArrayList<QuestionAnswerVO>? = arrayListOf(),
    var patient_id : String? = "",
    var dateTime : String? = "",
    var note : String? = "",
    var status : Boolean? = false

)