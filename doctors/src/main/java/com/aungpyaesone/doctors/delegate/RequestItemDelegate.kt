package com.aungpyaesone.doctors.delegate

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.DoctorVO

interface RequestItemDelegate {
    fun onTapAcceptButton(documentId:String,status:String,consultationRequestVO: ConsultationRequestVO,doctorVO: DoctorVO)
    fun onTapSkipButton(consultId:String)
    fun onTapLaterButton()
    fun onTapChooseTimeButton()
}