package com.aungpyaesone.patient.views.viewholders

import android.view.View
import com.aungpyaesone.patient.delegate.ShippingDelegate
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class ShippingAddressViewHolder(itemView: View,private val mPreviousPosition: Int,private val mDelegate: ShippingDelegate) : BaseViewHolder<String>(itemView) {

    override fun bindData(data: String) {

    }
}