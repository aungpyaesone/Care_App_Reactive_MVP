package com.aungpyaesone.patient.views.viewholders

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.aungpyaesone.patient.delegate.SpecialitiesQuestionDelegate
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.special_question_item_view.view.*

class SpecialQuestionViewHolder(itemView: View,private val mQuestionAnswerList : List<QuestionAnswerVO>,private val mDelegate: SpecialitiesQuestionDelegate) : BaseViewHolder<SpecialQuestionVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: SpecialQuestionVO) {
        mData = data
        itemView.tvQuestion.text = "(${adapterPosition+1}) ${data.question}"

        mQuestionAnswerList?.let {
            itemView.etAnswer.text = Editable.Factory.getInstance().newEditable(mQuestionAnswerList[adapterPosition].answer)
        }

        itemView.etAnswer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val questionAnswerVO= QuestionAnswerVO(data.id,data.question,itemView.etAnswer.text.toString())
                mDelegate.afterAnswer(adapterPosition,questionAnswerVO)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }



}
