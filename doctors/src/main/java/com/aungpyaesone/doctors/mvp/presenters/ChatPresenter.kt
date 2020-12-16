package com.aungpyaesone.doctors.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.views.ChatView
import com.aungpyaesone.doctors.views.view_pod.PatientItemViewPod
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.padc.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView> ,PatientItemViewPod.Delegate{
    fun onTapSendMessage(documentId: String,messageVO: ChatMessageVO)
    fun onTapGeneralMessageTemplate(documentId: String)
    fun onTapGiveMedicine(speciality:String)
    fun onTapNote()
    fun onReady(documentId:String,lifecycleOwner: LifecycleOwner)

}