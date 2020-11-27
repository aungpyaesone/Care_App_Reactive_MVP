package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.GeneralQuestionTypeConverter
import com.aungpyaesone.shared.persistence.QuestionTypeConverter
import com.aungpyaesone.shared.persistence.SpecialQuestionTypeConverter
import com.aungpyaesone.shared.persistence.SpecialitiesCollectionTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "specialites")
@IgnoreExtraProperties
data class SpecialitiesVO(
    @PrimaryKey
    var sp_id: String= "",
    var name: String = "",
    var photo: String = "",
)
