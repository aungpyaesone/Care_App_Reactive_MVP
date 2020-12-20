package com.aungpyaesone.patient.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.aungpyaesone.patient.delegate.CheckOutDelegate
import com.aungpyaesone.patient.delegate.ShippingDelegate
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.patient.mvp.view.CheckoutView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface CheckoutPresenter  : BasePresenter<CheckoutView>,CheckOutDelegate,ShippingDelegate{
    fun onUiReadyCheckout( consultationChatId : String ,  owner: LifecycleOwner)
    fun onTapCheckout(prescriotionList : List<PrescriptionVO>, deliveryAddressVO: String,
                      doctorVO: DoctorVO?, patientVO: PatientVO?, total_price : String)
    fun onTapAddShipping(patientVO: PatientVO?)
}