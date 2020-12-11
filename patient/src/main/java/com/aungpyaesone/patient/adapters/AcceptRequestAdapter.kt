package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.AcceptDoctorDelegate
import com.aungpyaesone.patient.views.viewholders.AcceptDoctorViewHolder
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class AcceptRequestAdapter(private val mDelegate: AcceptDoctorDelegate) : BaseAdapter<BaseViewHolder<ConsultationRequestVO>,ConsultationRequestVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ConsultationRequestVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.start_consultation_view_pod,parent,false)
        return AcceptDoctorViewHolder(v,mDelegate)
    }
}