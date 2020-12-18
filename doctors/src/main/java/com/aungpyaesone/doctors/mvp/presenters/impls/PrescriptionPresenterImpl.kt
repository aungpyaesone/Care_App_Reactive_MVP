package com.aungpyaesone.doctors.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.aungpyaesone.doctors.mvp.presenters.PrescriptionPresenter
import com.aungpyaesone.doctors.mvp.views.PrescriptionView
import com.aungpyaesone.shared.data.models.DoctorModel
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.MedicineVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter

class PrescriptionPresenterImpl : PrescriptionPresenter, AbstractBasePresenter<PrescriptionView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpls

    override fun getAllMedicine(
        lifecycleOwner: LifecycleOwner,
        documentId: String
    ) {
        mDoctorModel.getAllMedicineFromNetwork(documentId,onSuccess = {},onFailure = {})
        mDoctorModel.getAllMedicineFromDb().observe(lifecycleOwner, Observer {
            it?.let{
                    mView?.showMedicineList(it)
                }
        })
    }

    override fun onTapFinishConsultation(
        consultationChatVO: ConsultationChatVO,
        prescriptionList: List<PrescriptionVO>
    ) {
        mDoctorModel.finishConsultation(consultationChatVO,prescriptionList,onSuccess = {},onFailure = {})
        mView?.finishConsultation()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapAddMedicine(medicineVO: MedicineVO) {
        mView?.showRoutineDialog(medicineVO)
    }

    override fun onTapRemoveMedicine(medicineVO: MedicineVO) {
        mView?.removeMedicine(medicineVO)
    }
}