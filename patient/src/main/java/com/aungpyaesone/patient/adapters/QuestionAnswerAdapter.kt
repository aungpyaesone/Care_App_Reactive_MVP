package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.views.viewholders.QuestionAnswerViewHolder
import com.aungpyaesone.patient.views.viewholders.SpecialityViewHolder
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class QuestionAnswerAdapter : BaseAdapter<BaseViewHolder<QuestionAnswerVO>,QuestionAnswerVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<QuestionAnswerVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_answer_item_view,parent,false)
        return QuestionAnswerViewHolder(v)
    }
}