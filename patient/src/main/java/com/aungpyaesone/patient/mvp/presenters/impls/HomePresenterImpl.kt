package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.HomePresenter
import com.aungpyaesone.patient.mvp.view.HomeView
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter


class HomePresenterImpl  : HomePresenter,AbstractBasePresenter<HomeView>(){
    private val mCoreModel : CoreModel = CoreModelImpls

    init {
        mCoreModel.getSpecialityFromNetWork()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
      mCoreModel.getSpecialityFromDb().observe(lifecycleOwner, Observer {
          mView?.showSpecialitiesList(it)
      })
    }

    override fun onTapSpecialitiesItem(specialitiesVO: SpecialitiesVO) {
        mView?.showConfirmationDialog(specialitiesVO)
    }
}