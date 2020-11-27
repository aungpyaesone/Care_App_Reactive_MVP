package com.aungpyaesone.shared.data.vos
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "consultation_request")
@IgnoreExtraProperties
data class ConsultationRequestVO(
    @PrimaryKey
    var cr_id: String= "",
    var patient_id: String ? = "" ,
    var speciality : String ?= "",
    var date_time : String ?= "",
    var case_summary : String ?= ""
)