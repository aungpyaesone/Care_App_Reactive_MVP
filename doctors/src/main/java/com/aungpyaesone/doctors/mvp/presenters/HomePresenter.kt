package com.aungpyaesone.doctors.mvp.presenters

import android.content.Context
import com.aungpyaesone.doctors.delegate.AcceptPatientListDelegate
import com.aungpyaesone.doctors.delegate.RequestItemDelegate
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface HomePresenter : BasePresenter<HomeView>,RequestItemDelegate,AcceptPatientListDelegate{
    fun onTapPostponeTime(context: Context,str:String,consultationRequestVO:ConsultationRequestVO)
}