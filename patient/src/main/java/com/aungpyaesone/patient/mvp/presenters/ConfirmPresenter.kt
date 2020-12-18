package com.aungpyaesone.patient.mvp.presenters

import android.content.Context
import com.aungpyaesone.patient.mvp.view.ConfirmView
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface ConfirmPresenter : BasePresenter<ConfirmView> {
    fun onTapConfirmStartConsultation(context: Context)

}