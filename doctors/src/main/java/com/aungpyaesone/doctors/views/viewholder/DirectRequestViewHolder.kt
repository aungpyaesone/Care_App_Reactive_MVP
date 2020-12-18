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
import kotlinx.android.synthetic.main.request_item_view_two.view.*

class DirectRequestViewHolder(itemView: View, private val mDelegate: RequestItemDelegate) : BaseViewHolder<ConsultationRequestVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: ConsultationRequestVO) {
        mData = data
        data.patient?.let {
            itemView.tvOldPatient.text = itemView.context.getString(R.string.old_patient_label)
            itemView.tvPatientName.text = data.patient?.name
            itemView.tvPatientDob.text = data.patient?.dob
            data.patient?.photo?.let { itemView.ivPatientProfile.load(it.toUri(), R.drawable.image_placeholder) }
        }
    }

    init {
        itemView?.btnLater.setOnClickListener {
            mData?.let { mDelegate.onTapLaterButton(it.id) }
        }
        itemView.btnChooseTime.setOnClickListener {
            mData?.let {
                mDelegate.onTapChooseTimeButton(it)
            }
        }
        itemView.btnAccept.setOnClickListener {
            val doctorVO = SessionManager.get<DoctorVO>(sharePreferenceDoctor) ?: DoctorVO()
           // mData?.doctorVO = doctorVO
            mData?.let { mDelegate.onTapAcceptButton(itemView.context, it.id, "accept", it, doctorVO) }
        }
    }
}
