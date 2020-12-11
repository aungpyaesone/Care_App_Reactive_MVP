package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.request_item_view.view.*

class RequestViewHolder(itemView: View,private val mDelegate: RequestItemDelegate) : BaseViewHolder<ConsultationRequestVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: ConsultationRequestVO) {
        mData = data
        data.patient?.let {
            itemView.tvNewPatient.text = itemView.context.getString(R.string.new_patient_label)
            itemView.tvPatientName.text = data.patient?.name
            itemView.tvPatientDob.text = data.patient?.dob
            data.patient?.photo?.let{ itemView.ivPatientProfile.load(it.toUri(), R.drawable.image_placeholder) }
        }
    }

    init {
        itemView.btnSkip.setOnClickListener {
            mData?.let {
                mDelegate.onTapSkipButton(it.id)
            }
        }
        itemView.btnAccept.setOnClickListener {
            val doctorVO = SessionManager.get<DoctorVO>(sharePreferenceDoctor) ?: DoctorVO()
            mData?.let { mDelegate.onTapAcceptButton(it.id, "accept", it,doctorVO) }
        }
    }

}
