package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.padc.shared.mvp.views.BaseView

interface ConfirmView : BaseView {
    fun showPatientInfo(consultationRequestVO: ConsultationRequestVO)
    fun navigateToChatActivity()
}