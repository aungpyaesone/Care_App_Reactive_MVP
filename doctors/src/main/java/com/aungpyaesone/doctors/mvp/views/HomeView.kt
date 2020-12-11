package com.aungpyaesone.doctors.mvp.views

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.padc.shared.mvp.views.BaseView

interface HomeView : BaseView {
    fun showConsultationList(consultationList:List<ConsultationRequestVO>)
    fun showAcceptRequestList(consultationList: List<ConsultationRequestVO>)

    fun navigateToConfirmScreen(id:String)
}