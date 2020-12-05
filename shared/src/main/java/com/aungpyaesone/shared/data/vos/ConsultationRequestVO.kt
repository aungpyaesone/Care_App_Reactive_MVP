package com.aungpyaesone.shared.data.vos
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.converters.CaseSummaryTypeConverter
import com.aungpyaesone.shared.persistence.converters.PatientVOTypeConverter
import com.aungpyaesone.shared.persistence.converters.TimeStampTypeConverter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "consultation_request")
@IgnoreExtraProperties
@TypeConverters(
        CaseSummaryTypeConverter::class,
        PatientVOTypeConverter::class,
        TimeStampTypeConverter::class)
data class ConsultationRequestVO(
        @PrimaryKey
        var cr_id: String= "",
        var patient: PatientVO ? = null,
        var speciality : String ?= "",
        var date_time : Timestamp?= null,
        var case_summary : ArrayList<QuestionAnswerVO> ?= arrayListOf()
)
{

}