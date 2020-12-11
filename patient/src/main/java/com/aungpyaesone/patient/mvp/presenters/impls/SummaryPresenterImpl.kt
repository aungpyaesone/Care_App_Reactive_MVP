package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.aungpyaesone.shared.util.sharePreferencePatient
import com.aungpyaesone.shared.util.sharePreferenceQandA
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class SummaryPresenterImpl : SummaryPresenter, AbstractBasePresenter<SummaryView>() {
    private val mPatientModel: PatientModel = PatientModelImpls
    private val questionMap = hashMapOf<String?,QuestionAnswerVO>()

    init {
        // mPatientModel.getSpecialQuestionBySpecialities()
    }

    override fun getGeneralQuestion(oneTime: String, lifecycleOwner: LifecycleOwner) {

    }

    override fun getSpecialQuestion(speciality: String, lifecycleOwner: LifecycleOwner) {
        SessionManager.speciality = speciality
        mPatientModel.getSpecialQuestionBySpecialities(speciality, onSuccess = {}, onFailure = {
        mView?.showErrorMessage(it)
        })
        mPatientModel.getSpecialQuestionBySpecialitiesFromDb(speciality).observe(lifecycleOwner,
        Observer {
        mView?.showSpecialQuestion(it)
        })
    }

    override fun onTapContinue(
        speciality: String,
        lifecycleOwner: LifecycleOwner
    ) {

    }

    override fun onTapSendBroadCast(
            speciality: String,
            questionAnswerVO: List<QuestionAnswerVO>,
            specialQuestionVO: SpecialQuestionVO,
            patientVO: PatientVO,
            doctorVO: DoctorVO
    ) {
        mPatientModel.sendBroadCastConsultationRequest(speciality, questionAnswerVO, patientVO,doctorVO,
                System.currentTimeMillis().toString(), onSuccess = {}, onFailure = {})
    }

    override fun onTapStartConsultation(
        patientVO: PatientVO,
        questionAnswerList: List<QuestionAnswerVO>
    ) {
        SessionManager.putList(questionAnswerList, sharePreferenceQandA)
        SessionManager.put(patientVO, sharePreferencePatient)
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }

    override fun afterAnswer(position: Int, questionAnswerVO: QuestionAnswerVO) {
        mView?.showQuestionAnswer(position,questionAnswerVO)
    }


}