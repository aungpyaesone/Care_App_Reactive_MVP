package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.doctors.views.viewholder.AcceptRequestViewHolder
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class AcceptRequestAdapter(private val mDelegate: AcceptPatientListDelegate) : BaseAdapter<BaseViewHolder<ConsultationChatVO>, ConsultationChatVO>() {

    private var mConsultList : List<ConsultedPatientVO> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationChatVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.medicine_note_item_view,parent,false)
        return AcceptRequestViewHolder(v,mConsultList,mDelegate)
    }

}