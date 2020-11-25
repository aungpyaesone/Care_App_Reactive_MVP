package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.RoutineConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "medicine")
@TypeConverters(RoutineConverter::class)
data class MedicineVO(
        @PrimaryKey
        var id: String,
        var name: String? = "",
        var photo: String? ="",
        var routine: ArrayList<RoutineVO>? = arrayListOf(),
        var count: Long? = 0,
        var price: Long? = 0,
        var total_times: Long? = 0,
        var start_date: String? ="",
        var end_date: String? = ""
)