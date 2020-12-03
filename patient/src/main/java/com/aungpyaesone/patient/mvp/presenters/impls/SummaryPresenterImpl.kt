package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class SummaryPresenterImpl : SummaryPresenter,AbstractBasePresenter<SummaryView>() {
    private val mPatientModel : PatientModel = PatientModelImpls

    init {
       // mPatientModel.getSpecialQuestionBySpecialities()
    }

    override fun onTapContinue(
        speciality: String,
        lifecycleOwner: LifecycleOwner
    ) {
      mPatientModel.getSpecialQuestionBySpecialitiesFromDb(speciality).observe(lifecycleOwner,
          Observer {
              mView?.showSpecialQuestion(it)
          })
    }

    override fun onTapSendBroadCast(
        speciality: String,
        questionAnswerVO: QuestionAnswerVO,
        specialQuestionVO: SpecialQuestionVO,
        patientVO: PatientVO
    ) {

    }

    override fun onUiReady(speciality: String, lifecycleOwner: LifecycleOwner) {
        mPatientModel.getSpecialQuestionBySpecialities(speciality,onSuccess = {},onFailure = {
            mView?.showErrorMessage(it)
        })
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }


}