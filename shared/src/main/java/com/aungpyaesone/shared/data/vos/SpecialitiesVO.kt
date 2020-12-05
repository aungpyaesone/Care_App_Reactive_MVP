package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "specialites")
@IgnoreExtraProperties
data class SpecialitiesVO(
    @PrimaryKey
    var id: String= "",
    var name: String = "",
    var photo: String = "",
)
