package com.aungpyaesone.patient.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.patient.mvp.presenters.CheckoutPresenter
import com.aungpyaesone.patient.mvp.view.CheckoutView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter
import com.aungpyaesone.shared.util.sharePreferencePatient

class CheckOutPresenterImpl : CheckoutPresenter,AbstractBasePresenter<CheckoutView>() {
    private val patientModel = PatientModelImpls
    override fun onUiReadyCheckout(consultationChatId: String, owner: LifecycleOwner) {
        patientModel.getPrescriptionFromApi(consultationChatId, onSuccess = {}, onFailure = {})

        patientModel.getPrescriptionFromDb()
            .observe(owner, Observer {
                it?.let{
                    mView?.displayPrescription(it)
                }
            })

        patientModel.getPatientByEmail(SessionManager.patient_email.toString(), onSuccess = {}, onError = {})

        patientModel.getPatientFromDbByEmail(SessionManager.patient_email.toString())
            .observe(owner, Observer {
                it?.let{
                    SessionManager.put(it, sharePreferencePatient)
                    /***
                     * notice this place
                     */
                   // mView?.displayShippingAddress(it.address)
                }
            })
    }

    override fun onTapCheckout(
        prescriotionList: List<PrescriptionVO>,
        deliveryAddressVO: String,
        doctorVO: DoctorVO?,
        patientVO: PatientVO?,
        total_price: String
    ) {

    }

    override fun onTapAddShipping(patientVO: PatientVO?) {

    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapSelected(address: String, previousPosition: Int) {

    }
}