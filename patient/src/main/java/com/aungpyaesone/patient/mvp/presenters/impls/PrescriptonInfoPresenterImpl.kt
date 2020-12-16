package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.PrescriptonInfoPresenter
import com.aungpyaesone.patient.mvp.view.PrescriptionInfoView
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter
import java.util.ArrayList

class PrescriptonInfoPresenterImpl : PrescriptonInfoPresenter,AbstractBasePresenter<PrescriptionInfoView>() {

    private val mDoctorModel : DoctorModel = DoctorModelImpls

    override fun onUiReadyForPrescription(chatId: String, lifecycleOwner: LifecycleOwner) {
        mDoctorModel.getPrescriptionFromApi(chatId,onSuccess = {},onFailure = {})
        mDoctorModel.getPrescriptionFromDb().observe(lifecycleOwner, Observer {
            it?.let {
                mView?.displayPrescriptionList(it)
            }
        })

    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
    }
}