package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.extensions.load
import kotlinx.android.synthetic.main.start_consultation_view_pod.view.*

class ConsultationViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }

    private fun setUpListener() {
        btnStartConsult.setOnClickListener {
            mDelegate?.onTapConsultationStart()
        }
    }

    fun setViewPodData(doctorVO: DoctorVO){
        tvDoctorName.text = doctorVO.name
        tvTypeOfSpeciality.text = doctorVO.speciality
        tvBiography.text = doctorVO.biography
        doctorVO.photo?.toUri()?.let { ivProfile.load(it, R.drawable.ic_baseline_insert_photo_24) }
    }

    fun setDelegate(delegate: Delegate){
        mDelegate = delegate
    }



    interface Delegate {
        fun onTapConsultationStart()
    }
}