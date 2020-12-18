package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.views.viewholder.QuestionAnswerViewHolder
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class QuestionAnswerAdapter : BaseAdapter<BaseViewHolder<QuestionAnswerVO>, QuestionAnswerVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<QuestionAnswerVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_answer_item_view,parent,false)
        return QuestionAnswerViewHolder(v)
    }
}