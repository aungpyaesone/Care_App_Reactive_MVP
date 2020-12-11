package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.doctors.views.viewholder.AcceptRequestViewHolder
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class AcceptRequestAdapter(private val mDelegate: AcceptPatientListDelegate) : BaseAdapter<BaseViewHolder<ConsultationRequestVO>,ConsultationRequestVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationRequestVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.medicine_note_item_view,parent,false)
        return AcceptRequestViewHolder(v,mDelegate)
    }
}