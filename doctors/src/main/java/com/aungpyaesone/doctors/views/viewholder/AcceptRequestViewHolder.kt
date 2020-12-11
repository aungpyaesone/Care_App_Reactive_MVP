package com.aungpyaesone.doctors.views.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.padc.shared.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.medicine_note_item_view.view.*

class AcceptRequestViewHolder(itemView: View, private val mDelegate: AcceptPatientListDelegate) : BaseViewHolder<ConsultationRequestVO>(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bindData(data: ConsultationRequestVO) {
        mData = data
        data.patient?.let {
            itemView.tvPatientName.text = data.patient?.name
            itemView.tvPatientDob.text = data.patient?.dob
            data.patient?.photo?.let{ itemView.ivPatientProfile.load(it.toUri(), R.drawable.image_placeholder) }
        }
    }

    init {
            itemView.tvSendMessage.setOnClickListener{
                mData?.let {
                    mDelegate.onTapSendTextMessage()
                }
            }
            itemView.tvMedicineNote.setOnClickListener {
                mData?.let {
                    mDelegate.onTapMedicineNote()
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
    }



}
