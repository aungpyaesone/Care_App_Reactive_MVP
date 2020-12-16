package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.google.firebase.firestore.DocumentId
import com.padc.shared.mvp.views.BaseView

interface ConfirmView : BaseView {
    fun showPatientInfo(consultationRequestVO: ConsultationRequestVO)

    /***
     * @param documentId for chatting id
     */
    fun navigateToChatActivity(documentId: String)
}