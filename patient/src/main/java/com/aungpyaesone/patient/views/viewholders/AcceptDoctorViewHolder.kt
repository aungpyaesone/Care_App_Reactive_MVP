package com.aungpyaesone.patient.views.viewholders

import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.delegate.AcceptDoctorDelegate
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.checkSpeciality
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.start_consultation_view_pod.view.*

class AcceptDoctorViewHolder(itemView: View,private val mDelegate: AcceptDoctorDelegate) : BaseViewHolder<ConsultationRequestVO>(itemView){

    init {
        itemView.btnStartConsult.setOnClickListener {
            mData?.let {
                mDelegate.onTapStartConsultation(it)
            }
        }
    }
    override fun bindData(data: ConsultationRequestVO) {
        mData = data
        data.doctor?.let {
           it.photo?.let { photo -> itemView.ivProfile.load(photo.toUri(), R.drawable.image_placeholder) }
           itemView.tvDoctorName.text = it.name
           itemView.tvTypeOfSpeciality.text = checkSpeciality(it.speciality)
           itemView.tvBiography.text = it.biography
        }
    }


}