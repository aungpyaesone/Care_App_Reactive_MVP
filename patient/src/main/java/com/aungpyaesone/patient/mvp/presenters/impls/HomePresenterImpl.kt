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
        mCoreModel.getRecentlyConsultedDoctorFromApi("")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
      mView?.showLoading()
      mCoreModel.getSpecialityFromDb().observe(lifecycleOwner, Observer {
          mView?.hideLoading()
          mView?.showSpecialitiesList(it)
      })

      mCoreModel.getRecentlyConsultedDoctorFromDb().observe(lifecycleOwner, Observer {
          mView?.showRecentlyConsultedDoctor(it)
          mView?.hideLoading()
      })
    }

    override fun onTapSpecialitiesItem(specialitiesVO: SpecialitiesVO) {
        mView?.showConfirmationDialog(specialitiesVO)
    }
}