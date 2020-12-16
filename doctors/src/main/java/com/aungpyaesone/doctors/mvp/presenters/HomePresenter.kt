package com.aungpyaesone.doctors.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.padc.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,RequestItemDelegate,AcceptPatientListDelegate{

}