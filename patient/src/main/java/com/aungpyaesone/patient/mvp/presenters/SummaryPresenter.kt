package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.delegate.SpecialitiesQuestionDelegate
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO

interface SummaryPresenter : BasePresenter<SummaryView>,SpecialitiesQuestionDelegate {
    fun getGeneralQuestion(oneTime: String,lifecycleOwner: LifecycleOwner)
    fun getSpecialQuestion(speciality: String,lifecycleOwner: LifecycleOwner)
    fun onTapContinue(speciality:String,lifecycleOwner: LifecycleOwner)
    fun onTapSendBroadCast(speciality: String,questionAnswerVO: List<QuestionAnswerVO>,specialQuestionVO: SpecialQuestionVO,patientVO: PatientVO,doctorVO: DoctorVO)
    fun onTapStartConsultation(patientVO: PatientVO,questionAnswerList:List<QuestionAnswerVO>)
}