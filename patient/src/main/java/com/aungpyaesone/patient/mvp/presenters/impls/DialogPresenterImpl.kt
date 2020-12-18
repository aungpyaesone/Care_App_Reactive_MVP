package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.mvp.presenters.DialogPresenter
import com.aungpyaesone.patient.mvp.view.DialogView
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class DialogPresenterImpl : DialogPresenter, AbstractBasePresenter<DialogView>() {

    override fun onTapConfirmButton(specialitiesVO: SpecialitiesVO) {
        mView?.navigateToCaseSummaryScreen()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}