package com.aungpyaesone.doctors.mvp.presenters.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.dummy.doctorVO
import com.aungpyaesone.doctors.mvp.presenters.RegisterPresenter
import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.CoreModel
import com.aungpyaesone.shared.data.models.impls.AuthenticationModelImpls
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.padc.shared.mvp.presenter.AbstractBasePresenter

class RegisterPresenterImpls: RegisterPresenter, AbstractBasePresenter<RegisterView>() {
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpls
    private val mCoreModel : CoreModel = CoreModelImpls
    override fun onTapRegister(registerVO: DoctorVO, password: String) {
        mView?.showLoading()
        registerVO.email?.let {email->
        registerVO.name?.let { name ->
                mAuthenticationModel.register(email, password, name, onSuccess = {
                mView?.hideLoading()
                mCoreModel.addDoctor(registerVO,onSuccess = {
                    mCoreModel.saveDoctorToDb(registerVO)
                    Log.d("save","success save to data base")
                },onFailure = {})
                }, onFailure = {
                    mView?.showErrorMessage(it)
                })
            }
        }
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
     //  onTapRegister(doctorVO,"123456")
        getSpecialities()
    }

    private fun getSpecialities(){
        mCoreModel.getSpeciality(onSuccess = {
            Log.d("specilities","success")
        },onFailure = {
            Log.d("specilities",it)
        })
    }


}