package com.aungpyaesone.doctors.delegate

import com.aungpyaesone.shared.data.vos.GeneralQuestionVO

interface GeneralQuestionDelegate {
    fun onTapGeneralQuestion(generalQuestionVO: GeneralQuestionVO)
}