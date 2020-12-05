package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "doctor")
data class DoctorVO(
    @PrimaryKey
    var id: String = "",
    var name: String? = "",
    var photo: String? ="",
    var biography: String? ="",
    var degree : String? ="",
    var experience : String? = "",
    var address : String? ="",
    var speciality : String? ="",
    var email: String? ="",
    var deviceId:String? = "",
    var phone:String? ="",
){

}