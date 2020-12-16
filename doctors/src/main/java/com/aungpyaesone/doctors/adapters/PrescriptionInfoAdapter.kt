package com.aungpyaesone.doctors.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.views.viewholder.PrescriptionInfoViewHolder
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class PrescriptionInfoAdapter(): BaseAdapter<BaseViewHolder<PrescriptionVO>,PrescriptionVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PrescriptionVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prescription_info_item_view,parent,false)
        return PrescriptionInfoViewHolder(v)
    }
}