package com.aungpyaesone.doctors.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.delegate.MedicineDelegate
import com.aungpyaesone.doctors.mvp.views.PrescriptionView
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.padc.shared.mvp.presenter.BasePresenter

interface PrescriptionPresenter: BasePresenter<PrescriptionView>,MedicineDelegate {
    fun getAllMedicine(lifecycleOwner: LifecycleOwner,documentId:String)
    fun onTapFinishConsultation(consultationChatVO: ConsultationChatVO,prescriptionList:List<PrescriptionVO>)
}