package com.aungpyaesone.doctors.views.view_pod

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.net.toUri
import com.aungpyaesone.doctors.R
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.extensions.load
import kotlinx.android.synthetic.main.prescription_view_pod.view.*

class PrescriptionViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }

    fun setPrescriptionData(prescription: List<PrescriptionVO>, doctorPhoto: String) {
        pdoctor_photo.load(doctorPhoto.toUri(), R.drawable.image_placeholder)
        var str: String = ""
        if (prescription.isNotEmpty()) {
            for (item in prescription) {
                str += item.medicine_name + "\n"
            }
        }
        txt_medicineList.text = str.toString()
    }

    fun setDelegate(delegate: Delegate) {
        mDelegate = delegate
    }

    private fun setUpListener() {
//        btn_prescription.setOnClickListener {
//            mDelegate?.onTapPrescription()
//        }

    }

    interface Delegate {
        fun onTapPrescription()
    }
}