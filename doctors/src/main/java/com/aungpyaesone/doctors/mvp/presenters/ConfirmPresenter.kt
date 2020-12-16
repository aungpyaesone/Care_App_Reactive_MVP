package com.aungpyaesone.doctors.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.views.ConfirmView
import com.padc.shared.mvp.presenter.BasePresenter


interface ConfirmPresenter : BasePresenter<ConfirmView> {
    fun onUIReady(id:String,lifecycleOwner: LifecycleOwner)
    fun onTapStartConsultation(context: Context)
}