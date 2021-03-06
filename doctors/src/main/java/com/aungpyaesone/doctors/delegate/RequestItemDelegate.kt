package com.aungpyaesone.doctors.delegate

import android.content.Context
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.DoctorVO

interface RequestItemDelegate {
    fun onTapAcceptButton(context: Context,documentId:String,status:String,consultationRequestVO: ConsultationRequestVO,doctorVO: DoctorVO)
    fun onTapSkipButton(consultId:String)
    fun onTapLaterButton(consultId:String)
    fun onTapChooseTimeButton(consultationRequestVO: ConsultationRequestVO)
}