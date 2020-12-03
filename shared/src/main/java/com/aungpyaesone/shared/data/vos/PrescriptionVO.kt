package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.converters.RoutineConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "prescription")
@IgnoreExtraProperties
@TypeConverters(RoutineConverter::class)
data class PrescriptionVO(
    @PrimaryKey
    var id:String ="",
    var days: String? ="",
    var medicine_name :String? = "",
    var routineVO: RoutineVO? = null,
    var totalTime: Int? = 0
)