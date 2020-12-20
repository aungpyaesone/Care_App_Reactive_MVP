package com.aungpyaesone.patient.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.CheckOutDelegate
import com.aungpyaesone.patient.views.viewholders.MedicineListViewHolder
import com.aungpyaesone.shared.adapters.BaseAdapter
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder

class CheckoutAdapter(private val mDelegate:CheckOutDelegate) : BaseAdapter<BaseViewHolder<PrescriptionVO>,PrescriptionVO>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PrescriptionVO> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkout_medicine_item_view, parent, false)
        return MedicineListViewHolder(view, mDelegate)
    }
}