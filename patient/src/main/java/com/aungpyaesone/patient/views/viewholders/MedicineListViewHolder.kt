package com.aungpyaesone.patient.views.viewholders

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.patient.delegate.CheckOutDelegate
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.checkout_medicine_item_view.view.*
import kotlin.time.times

class MedicineListViewHolder(itemView: View,private val mDelegate:CheckOutDelegate) : BaseViewHolder<PrescriptionVO>(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bindData(data: PrescriptionVO) {
        data?.let {
            itemView.txt_price.text = (it.count?.toInt()?.let { it1 -> it.price?.toInt()?.times(it1) }).toString()
            itemView.txt_tablet.text = data.count +" Tablet"
            itemView.txt_medicinename.text = it.medicine_name
        }
    }
}