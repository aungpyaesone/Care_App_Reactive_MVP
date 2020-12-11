package com.aungpyaesone.patient.delegate

import com.aungpyaesone.shared.data.vos.QuestionAnswerVO

interface SpecialitiesQuestionDelegate {
    fun afterAnswer(position:Int,questionAnswerVO: QuestionAnswerVO)
}