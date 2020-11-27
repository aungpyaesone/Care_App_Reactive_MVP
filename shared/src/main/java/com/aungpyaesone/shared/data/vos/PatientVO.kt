package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "patient")
data class PatientVO(
    @PrimaryKey
    var id:String= "" ,
    var name:String? = "",
    var photo: String? = "",
    var address : String? = "",
    var gender : String? = "",
    var dob : String? = "",
    var age : String? = "",
    var phone : String? = "",
    var email: String? ="",
)