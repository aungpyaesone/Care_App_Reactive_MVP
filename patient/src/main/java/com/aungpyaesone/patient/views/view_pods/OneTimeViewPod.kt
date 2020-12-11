package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.aungpyaesone.shared.data.vos.PatientVO
import kotlinx.android.synthetic.main.one_time_view_pod.view.*

class OneTimeViewPod @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)
{
    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setData(patientVO: PatientVO){
        tvPName.text = patientVO.name
        tvPDob.text = patientVO.dob
        tvPAllergic.text = patientVO.allergic_medicine
        tvPbloodType.text = patientVO.blood_pressure
        tvPHeight.text = patientVO.height
    }
}