package com.aungpyaesone.patient.mvp.view

import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.mvp.views.BaseView

interface CheckoutView : BaseView {
    fun displayPrescription(list: List<PrescriptionVO>)
    fun displayShippingAddress (list : List<String>)
    fun displayConfirmDialog(list: List<PrescriptionVO>, total_price: String, address: String)
    fun selectedShippingAddress(address: String, previousPostion: Int)
}