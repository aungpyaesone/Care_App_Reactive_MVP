package com.aungpyaesone.doctors.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.doctors.mvp.views.ConfirmView
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class ConfirmPresenterImpl : ConfirmPresenter, AbstractBasePresenter<ConfirmView>() {
    private val mDoctorModel = DoctorModelImpls
    private val mCoreModel = CoreModelImpls
    private var mChatId : String? = null
    override fun onUIReady(id: String, lifecycleOwner: LifecycleOwner) {
        mCoreModel.getConsultationRequestByIdFromDb(id).observe(lifecycleOwner, Observer {
            it?.let {
                mView?.showPatientInfo(consultationRequestVO = it)
                mChatId = it.consultationchat_id
            }
        })
    }

    override fun onTapStartConsultation(context: Context) {
        mChatId?.let {
            mView?.navigateToChatActivity(it)
        }
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

}