package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.RoutineConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "medicine")
data class MedicineVO(
        @PrimaryKey
        var id: String,
        var name: String? = "",
        var price: Int? = 0,
)