package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.doctors.delegate.GeneralQuestionDelegate
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.question_answer_item_view.view.*
import kotlinx.android.synthetic.main.question_answer_item_view.view.tvQuestion
import kotlinx.android.synthetic.main.question_template_item_view.view.*

class GeneralQuestionViewHolder(itemView: View,private val mDeGeneralQuestionDelegate: GeneralQuestionDelegate) : BaseViewHolder<GeneralQuestionVO>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bindData(data: GeneralQuestionVO) {
        mData = data
        itemView.tvQuestion.text = data.question
    }

    init {
        itemView.setOnClickListener {
            mData?.let {
                mDeGeneralQuestionDelegate.onTapGeneralQuestion(it)
            }
        }
    }





}
