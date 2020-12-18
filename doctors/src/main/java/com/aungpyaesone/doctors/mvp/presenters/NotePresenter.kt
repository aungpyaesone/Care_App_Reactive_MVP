package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.NoteView
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface NotePresenter : BasePresenter<NoteView> {
    fun onTapWriteNote(id:String,consultationChatVO: ConsultationChatVO)
}