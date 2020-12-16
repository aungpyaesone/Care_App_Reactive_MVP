package com.aungpyaesone.patient.views.viewholders

import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.ConsultationDelegate
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.checkSpeciality
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.consulted_item_view.view.*
import java.text.DateFormat

class ConsultationViewHolder(itemView: View, private val mDelegate: ConsultationDelegate) : BaseViewHolder<ConsultationChatVO>(itemView){

    init {
        itemView.tvPrescription.setOnClickListener {
            mData?.let {
                mDelegate.onTapPrescription(it)
            }
        }
        
        itemView.tvSendMessage.setOnClickListener { 
            mData?.let{
                mDelegate.onTapSendMessage(it)
            }
        }
    }
    override fun bindData(data: ConsultationChatVO) {
        mData = data
        data.doctor?.let {
           it.photo?.let { photo -> itemView.ivPatientProfile.load(photo.toUri(), R.drawable.image_placeholder) }
           itemView.tvPatientName.text = it.name
           itemView.tvPatientDob.text = checkSpeciality(it.speciality)
           itemView.startConsultDate.text = DateFormat.getDateInstance().format(data.dateTime?.toLong()).toString()
        }
    }


}