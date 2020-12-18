package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.MedicineDelegate
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.medicine_item_view.view.*

class MedicineViewHolder(itemView: View, private val mDelegate: MedicineDelegate) : BaseViewHolder<MedicineVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: MedicineVO) {
      mData = data
        itemView.tvMedicineName.text = data.name
        if(data.is_selected == null){
            data.is_selected = false
        }
        if(data.is_selected == false){
            itemView.ivAdd.setImageResource(R.drawable.add)
        }
        else{
            itemView.ivAdd.setImageResource(R.drawable.minussign)
        }


        itemView.ivAdd.setOnClickListener {

                if(data.is_selected == false){
                    itemView.ivAdd.setImageResource(R.drawable.minussign)
                    mDelegate.onTapAddMedicine(data)
                    data.is_selected= true
                }
                else {
                    itemView.ivAdd.setImageResource(R.drawable.add)
                    data.is_selected = false
                    mDelegate.onTapRemoveMedicine(data)
                }
        }
    }

    }

