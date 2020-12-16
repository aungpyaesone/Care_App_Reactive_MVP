package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.patient.views.view_pods.PrescriptionViewPod
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.padc.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView>,PrescriptionViewPod.Delegate {
    fun onTapSendMessage(chatId: String,messageVO: ChatMessageVO)
    fun onTapStartConsultation()
    fun onTapPrescribeMedicine()
    fun onReady(chatId:String,lifecycleOwner: LifecycleOwner)
}