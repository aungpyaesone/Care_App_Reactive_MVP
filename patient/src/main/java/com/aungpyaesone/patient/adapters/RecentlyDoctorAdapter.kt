package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.RecentlyDoctorDelegate
import com.aungpyaesone.patient.views.viewholders.RecentDoctorViewHolder
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class RecentlyDoctorAdapter(private val mDelegate: RecentlyDoctorDelegate) : BaseAdapter<BaseViewHolder<RecentDoctorVO>, RecentDoctorVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecentDoctorVO> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recent_doctor_item_view,parent,false)
        return RecentDoctorViewHolder(v,mDelegate)
    }
}