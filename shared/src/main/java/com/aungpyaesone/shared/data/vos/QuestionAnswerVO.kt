package com.aungpyaesone.shared.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class QuestionAnswerVO(
    var id : String="",
    var question: String?= "",
    var answer: String?= ""
)