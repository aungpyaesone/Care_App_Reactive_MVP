package com.aungpyaesone.patient.views.viewholders

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.question_answer_item_view.view.*
import kotlinx.android.synthetic.main.special_question_item_view.view.*
import kotlinx.android.synthetic.main.special_question_item_view.view.tvQuestion

class QuestionAnswerViewHolder(itemView: View) : BaseViewHolder<QuestionAnswerVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: QuestionAnswerVO) {
        mData = data
        itemView.tvQuestion.text = "(${adapterPosition+1}) ${data.question}"
        itemView.tvAnswer.text = data.answer
    }



}
