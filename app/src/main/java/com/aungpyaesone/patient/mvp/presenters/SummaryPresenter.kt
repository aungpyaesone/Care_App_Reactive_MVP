package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.shared.data.vos.PatientVO
import com.padc.shared.mvp.presenter.BasePresenter
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO

interface SummaryPresenter : BasePresenter<SummaryView> {
    fun onUiReady(speciality: String,lifecycleOwner: LifecycleOwner)
    fun onTapContinue(speciality:String,lifecycleOwner: LifecycleOwner)
    fun onTapSendBroadCast(speciality: String,questionAnswerVO: QuestionAnswerVO,specialQuestionVO: SpecialQuestionVO,patientVO: PatientVO)
}