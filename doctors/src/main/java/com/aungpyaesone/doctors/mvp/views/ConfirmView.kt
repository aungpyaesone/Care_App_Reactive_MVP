package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface ConfirmView : BaseView {
    fun showPatientInfo(consultationRequestVO: ConsultationRequestVO)

    /***
     * @param documentId for chatting id
     */
    fun navigateToChatActivity(documentId: String)
}