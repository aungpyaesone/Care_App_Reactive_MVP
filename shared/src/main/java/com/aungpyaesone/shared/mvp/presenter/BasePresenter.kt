package com.padc.shared.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.padc.shared.mvp.views.BaseView

interface BasePresenter<T: BaseView> {
    fun initPresenter(view: T)
    fun onUiReady(lifecycleOwner: LifecycleOwner)
}