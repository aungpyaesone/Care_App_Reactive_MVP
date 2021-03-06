package com.aungpyaesone.shared.mvp.presenter

import androidx.lifecycle.ViewModel
import com.aungpyaesone.shared.mvp.views.BaseView

abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel(){
    var mView : T? = null
   // var mVO : W? = null

    override fun initPresenter(view: T) {
        mView = view
    }

}