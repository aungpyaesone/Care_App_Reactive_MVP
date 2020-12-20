package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.ShippingDelegate
import com.aungpyaesone.patient.views.viewholders.ShippingAddressViewHolder
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class ShippingAddressAdapter( private val mDelegate: ShippingDelegate, var mpreviousPosition: Int) : BaseAdapter<BaseViewHolder<String>,String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shipping_address_item_view, parent, false)
        return ShippingAddressViewHolder(view,  mpreviousPosition, mDelegate)
    }
}