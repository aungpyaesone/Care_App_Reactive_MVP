package com.aungpyaesone.patient.views.viewholders

import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.SpecialitiesDelegate
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.extensions.load
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.specialities_item_view.view.*

class SpecialityViewHolder(itemView: View, private val mDelegate: SpecialitiesDelegate) :
    BaseViewHolder<SpecialitiesVO>(itemView) {

    init {
        itemView.setOnClickListener {
            mData?.let {
                mDelegate.onTapSpecialitiesItem(it)
            }
        }
    }

    override fun bindData(data: SpecialitiesVO) {
        mData = data
        itemView.ivSpeciality.load(data.photo.toUri(), R.drawable.ic_baseline_insert_photo_24)
        itemView.tvSpeciality.text = data.name
    }

}