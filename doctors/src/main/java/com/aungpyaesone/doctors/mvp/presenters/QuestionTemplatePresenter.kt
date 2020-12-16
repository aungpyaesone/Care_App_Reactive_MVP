package com.aungpyaesone.doctors.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.delegate.GeneralQuestionDelegate
import com.aungpyaesone.doctors.delegate.MedicineDelegate
import com.aungpyaesone.doctors.mvp.views.PrescriptionView
import com.aungpyaesone.doctors.mvp.views.QuestionTemplateView
import com.padc.shared.mvp.presenter.BasePresenter

interface QuestionTemplatePresenter : BasePresenter<QuestionTemplateView>,GeneralQuestionDelegate {
    fun onReady(documentId:String,lifecycleOwner: LifecycleOwner)
}