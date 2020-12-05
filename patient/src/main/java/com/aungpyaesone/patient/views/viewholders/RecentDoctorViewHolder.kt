package com.aungpyaesone.patient.views.viewholders

import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.RecentlyDoctorDelegate
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.extensions.load
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.recent_doctor_item_view.view.*

class RecentDoctorViewHolder(itemView: View,val mDelegate: RecentlyDoctorDelegate) : BaseViewHolder<RecentDoctorVO>(itemView){

    init {
        itemView.setOnClickListener {
            mData?.let {
                mDelegate.onTapRecentlyDoctor(it)
            }
        }
    }

    override fun bindData(data: RecentDoctorVO) {
        mData = data
        data.photo?.toUri()?.let { itemView.ivProfile.load(it, R.drawable.ic_baseline_insert_photo_24) }
        itemView.tvName.text = data.name
        itemView.tvSpeciality.text = data.speciality

    }
}