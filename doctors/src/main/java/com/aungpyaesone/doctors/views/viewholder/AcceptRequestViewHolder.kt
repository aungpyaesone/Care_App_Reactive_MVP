package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.extensions.load
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.medicine_note_item_view.view.*
import java.text.DateFormat

class AcceptRequestViewHolder(itemView: View, private val mDelegate: AcceptPatientListDelegate) : BaseViewHolder<ConsultationChatVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: ConsultationChatVO) {
        mData = data
        data.patient?.let {
            itemView.tvPatientName.text = data.patient?.name
            itemView.tvPatientDob.text = DateFormat.getDateInstance().format(data.dateTime?.toLong()).toString()
            data.patient?.photo?.let{ itemView.ivPatientProfile.load(it.toUri(), R.drawable.image_placeholder) }
        }
    }

    init {
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
                    mDelegate.onTapPrescribe(it)
                }
            }

        itemView.tvNote.setOnClickListener {
            mData?.let {
                mDelegate.onTapNote(it)
            }
        }
    }



}
