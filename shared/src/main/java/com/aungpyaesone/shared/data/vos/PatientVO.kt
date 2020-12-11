package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.converters.TimeStampTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "patient")
@TypeConverters(TimeStampTypeConverter::class)
data class PatientVO(
    @PrimaryKey
    var id:String= "" ,
    var name:String? = "",
    var photo: String? = "",
    var dob : String? = "",
    var blood_type : String? ="",
    var blood_pressure: String? ="",
    var email: String? ="",
    var deviceId:String? ="",
    var height : String? = null,
    var weight : String? ="",
    var allergic_medicine : String? ="",
    var created_date : String? = null
) {

}

