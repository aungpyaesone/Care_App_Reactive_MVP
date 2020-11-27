package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.QuestionTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "general_question_template")
@IgnoreExtraProperties
@TypeConverters(QuestionTypeConverter::class)
data class GeneralQuestionTemplateVO(
    @PrimaryKey
    var gq_id: String= "",
    var type: String = "",
    var title: String = "",
    var questions: ArrayList<QuestionVO> = arrayListOf()
)

data class QuestionVO(
    var question : String ? =""
)