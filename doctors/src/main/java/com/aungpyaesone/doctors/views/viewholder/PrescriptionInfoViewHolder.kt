package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.prescription_info_item_view.view.*

class PrescriptionInfoViewHolder(itemView: View) : BaseViewHolder<PrescriptionVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: PrescriptionVO) {
        mData = data
        data?.let {
            itemView.medicine_name.text = data.medicine_name
            itemView.amount.text  = data?.routineVO?.amount +" mg"
            itemView.txt_quality.text = data.routineVO?.tab + " Tablet"
            itemView.txt_time.text = data.routineVO?.day
            val times = data.routineVO?.times.toString()
            itemView.txt_count.text = times
            itemView.txt_repeat.text  = data.routineVO?.repeat
            itemView.txt_comment.text = data.routineVO?.note
        }
    }

    /*init {
            itemView.tvSendMessage.setOnClickListener{
                mData?.let {
                    it.id?.let {chatId ->
                        mDelegate.onTapSendTextMessage(chatId)
                    }

                }
            }
            itemView.tvMedicineNote.setOnClickListener {
                mData?.let {
                    mDelegate.onTapMedicineNote(it)
                }
            }

            itemView.tvPrescribe.setOnClickListener {
                mData?.let {
                    mDelegate.onTapPrescribe()
                }
            }

        itemView.tvNote.setOnClickListener {
            mData?.let {
                mDelegate.onTapNote()
            }
        }
    }*/



}
