package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.padc.shared.mvp.views.BaseView

interface PrescriptionInfoView : BaseView {
    fun displayPrescriptionList(prescription_list: List<PrescriptionVO>)

}