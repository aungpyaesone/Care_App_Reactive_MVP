package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.SpecialitiesQuestionDelegate
import com.aungpyaesone.patient.views.viewholders.SpecialQuestionViewHolder
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class SpecialQuestionAdapter(private val mDelegate: SpecialitiesQuestionDelegate) : BaseAdapter<BaseViewHolder<SpecialQuestionVO>,SpecialQuestionVO>() {

    var mQuestionAnswerList: List<QuestionAnswerVO> = arrayListOf()

    fun setQuestionAnswerList( questionAnswerList: List<QuestionAnswerVO>)
    {
        mQuestionAnswerList = questionAnswerList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SpecialQuestionVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.special_question_item_view,parent,false)
        return SpecialQuestionViewHolder(v,mQuestionAnswerList,mDelegate)
    }
}