package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.patient.views.view_pods.PatientItemViewPod
import com.aungpyaesone.patient.views.view_pods.PrescriptionViewPod
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface ChatPresenter : BasePresenter<ChatView>,PrescriptionViewPod.Delegate,PatientItemViewPod.Delegate {
    fun onTapSendMessage(chatId: String,messageVO: ChatMessageVO)
    fun onTapStartConsultation()
    fun onTapPrescribeMedicine()
    fun onReady(chatId:String,lifecycleOwner: LifecycleOwner)
}