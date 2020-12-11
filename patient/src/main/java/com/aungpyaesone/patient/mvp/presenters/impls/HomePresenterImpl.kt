package com.aungpyaesone.patient.mvp.presenters.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.HomePresenter
import com.aungpyaesone.patient.mvp.view.HomeView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter


class HomePresenterImpl  : HomePresenter,AbstractBasePresenter<HomeView>(){
    private val mCoreModel : CoreModel = CoreModelImpls
    private val mPatientModel : PatientModel = PatientModelImpls

    init {
        mCoreModel.getSpecialityFromNetWork(onSuccess = {}, onFailure = {
            mView?.showErrorMessage(it)
        })
       /* mCoreModel.getRecentlyConsultedDoctorFromApi("",onSuccess = {},onFailure = {
            mView?.showErrorMessage(it)
        } )*/
    }

    override fun onTapPositiveButton() {
        Log.d("positive","it is okay")
        mView?.navigateToCaseSummary()
    }

    override fun onTapNegativeButton() {
        TODO("Not yet implemented")
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
      mView?.showLoading()
      mCoreModel.getSpecialityFromDb().observe(lifecycleOwner, Observer {
          mView?.hideLoading()
          mView?.showSpecialitiesList(it)
      })

      mCoreModel.getRecentlyConsultedDoctorFromDb().observe(lifecycleOwner, Observer {
          mView?.showRecentlyConsultedDoctor(it)
      })

      mPatientModel.getAllConsultationRequestFromApi(SessionManager.patient_id.toString(),onSuccess = {},onFailure = {mView?.showErrorMessage(it)})
      mCoreModel.getAllDoctorAcceptConsultationRequestFromDb().observe(lifecycleOwner, Observer {
          it?.let {
              mView?.showAcceptDoctorList(it)
          }
      })
    }

    override fun onTapSpecialitiesItem(specialitiesVO: SpecialitiesVO) {
        mView?.showConfirmationDialog(specialitiesVO)
    }

    override fun onTapStartConsultation() {
        TODO("Not yet implemented")
    }

    override fun onTapRecentlyDoctor(recentDoctorVO: RecentDoctorVO) {
       TODO("Not yet implemented")
    }

    override fun onTapConsultationStart() {
        TODO("Not yet implemented")
    }
}