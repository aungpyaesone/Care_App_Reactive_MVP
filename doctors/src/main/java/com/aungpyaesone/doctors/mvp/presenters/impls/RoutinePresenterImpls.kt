package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.mvp.presenters.RoutinePresenter
import com.aungpyaesone.doctors.mvp.views.RoutineView
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class RoutinePresenterImpls : RoutinePresenter, AbstractBasePresenter<RoutineView>() {
    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        TODO("Not yet implemented")
    }
}