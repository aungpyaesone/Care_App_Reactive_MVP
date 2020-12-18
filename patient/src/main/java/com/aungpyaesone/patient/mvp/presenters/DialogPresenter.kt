package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.DialogView
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface DialogPresenter : BasePresenter<DialogView> {
    fun onTapConfirmButton(specialitiesVO: SpecialitiesVO)
}