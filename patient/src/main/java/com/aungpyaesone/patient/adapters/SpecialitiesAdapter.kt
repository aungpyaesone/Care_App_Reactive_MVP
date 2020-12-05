package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.SpecialitiesDelegate
import com.aungpyaesone.patient.views.viewholders.SpecialityViewHolder
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.padc.shared.adapters.BaseAdapter
import com.padc.shared.viewholders.BaseViewHolder

class SpecialitiesAdapter(private val mDelegate:SpecialitiesDelegate) : BaseAdapter<BaseViewHolder<SpecialitiesVO>,SpecialitiesVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SpecialitiesVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.specialities_item_view,parent,false)
        return SpecialityViewHolder(v,mDelegate)
    }
}