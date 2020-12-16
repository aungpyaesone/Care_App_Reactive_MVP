package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.ConsultationDelegate
import com.aungpyaesone.patient.views.viewholders.ChatItemViewHolder
import com.aungpyaesone.patient.views.viewholders.ConsultationViewHolder
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class ConsultationAdapter(private val consultationDelegate: ConsultationDelegate) : BaseAdapter<BaseViewHolder<ConsultationChatVO>, ConsultationChatVO>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationChatVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.consulted_item_view,parent,false)
        return ConsultationViewHolder(v,consultationDelegate)
    }
}