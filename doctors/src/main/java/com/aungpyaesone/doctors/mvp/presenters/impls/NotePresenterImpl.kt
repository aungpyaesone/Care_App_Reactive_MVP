package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.NotePresenter
import com.aungpyaesone.doctors.mvp.views.NoteView
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class NotePresenterImpl : NotePresenter,AbstractBasePresenter<NoteView>() {
    private val mCoreModel : CoreModel = CoreModelImpls
    private val mDoctorModel : DoctorModel = DoctorModelImpls
    override fun onTapWriteNote(
        id: String,
        consultationChatVO: ConsultationChatVO
    ) {
        /***
         * update consultation chat with consulted date
         */
       mCoreModel.updateConsultationChat(consultationChatVO,onSuccess = {
           mView?.navigateToChatActivity()
       },onFailure = {})
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }


}