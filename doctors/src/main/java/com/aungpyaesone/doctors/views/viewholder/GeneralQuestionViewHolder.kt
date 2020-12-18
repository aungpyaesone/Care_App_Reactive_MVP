package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.doctors.delegate.GeneralQuestionDelegate
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.question_answer_item_view.view.tvQuestion

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
