package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.GeneralQuestionDelegate
import com.aungpyaesone.doctors.views.viewholder.GeneralQuestionViewHolder
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class GeneralQuestionAdapter (private val mDelegate:GeneralQuestionDelegate): BaseAdapter<BaseViewHolder<GeneralQuestionVO>, GeneralQuestionVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GeneralQuestionVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_template_item_view,parent,false)
        return GeneralQuestionViewHolder(v,mDelegate)
    }
}