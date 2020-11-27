package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "general_question_template")
@IgnoreExtraProperties
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