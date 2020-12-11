package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.padc.shared.mvp.views.BaseView

interface SummaryView : BaseView {
    fun showSpecialQuestion(specialQuestionList:List<SpecialQuestionVO>)
    fun navigateToChatScreen()
    fun showQuestionAnswer(position:Int,questionAnswer: QuestionAnswerVO)
    fun showFirstLayout()
    fun showSecondLayout()

}