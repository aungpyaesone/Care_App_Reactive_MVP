package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.net.toUri
import com.aungpyaesone.patient.R
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.extensions.load
import kotlinx.android.synthetic.main.prescription_view_pod.view.*

class PrescriptionViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null
    private var chatId : String? = ""

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()
    }

    fun setPrescriptionData(prescription: List<PrescriptionVO>, doctorPhoto: String,consuChatId:String) {
        pdoctor_photo.load(doctorPhoto.toUri(), R.drawable.image_placeholder)
        chatId = consuChatId
        var str: String = ""
        if (prescription.isNotEmpty()) {
            for (item in prescription) {
                str += item.medicine_name + "\n"
            }
        }
        txt_medicineList.text = str.toString()
    }

    fun setDelegate(delegate: Delegate) {
        this.mDelegate = delegate
    }

    private fun setUpListener() {
        btn_prescription.setOnClickListener {
            chatId?.let { it1 -> mDelegate?.onTapPrescription(it1) }
        }

    }

    interface Delegate {
        fun onTapPrescription(chatId:String)
    }
}