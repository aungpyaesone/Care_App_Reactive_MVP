package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface HomeView : BaseView {
    fun showSpecialitiesList(specialitiesList:List<SpecialitiesVO>)
    fun showRecentlyConsultedDoctor(recentlyDoctorList:List<RecentDoctorVO>)
    fun showConfirmationDialog(specialitiesVO: SpecialitiesVO)
    fun showConConfirmDialog(recentDoctorVO: RecentDoctorVO)
    fun showAcceptDoctorList(consultationRequestList:List<ConsultationRequestVO>)
    fun navigateToCaseSummary()
    fun navigateToChatActivity(consultationRequestVO: ConsultationRequestVO)
}