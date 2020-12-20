package com.aungpyaesone.patient.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.CheckoutAdapter
import com.aungpyaesone.patient.mvp.presenters.CheckoutPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.CheckOutPresenterImpl
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.checkout_dialog.view.*

class CheckoutDialog : DialogFragment() {
    private lateinit var mPresenter: CheckoutPresenter

    companion object {

        private const val KEY_prescripitonList = "prescripitonList"
        private const val KEY_SHIPPING_ADDRESS = "address"
        private const val KEY_TOTAL = "total"


        fun newInstance(prescripitonList: String, shippingAddress: String,total_price: String): CheckoutDialog {
            val args = Bundle()
            args.putString(KEY_prescripitonList, prescripitonList)
            args.putString(KEY_SHIPPING_ADDRESS, shippingAddress)
            args.putString(KEY_TOTAL, total_price)
            val fragment = CheckoutDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.checkout_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.apply {
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun setupView(view: View) {

        view.fulladdress.text =  arguments?.getString(KEY_SHIPPING_ADDRESS)
        view.total_amounts.text =  arguments?.getString(KEY_TOTAL)
        view.subtotal.text =  arguments?.getString(KEY_TOTAL)

        view.prescription_rcy?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mPresenter = ViewModelProviders.of(this).get(CheckOutPresenterImpl::class.java)
        var adapter = CheckoutAdapter(mPresenter)
        view.prescription_rcy?.adapter = adapter
        view.prescription_rcy?.setHasFixedSize(false)

        var data = arguments?.getString(KEY_prescripitonList)
        val gson = Gson()
        var list = gson.fromJson(data, Array<PrescriptionVO>::class.java).toList()
        adapter.setData(list.toMutableList())

    }

    private fun setupClickListeners(view: View) {
        view.btn_payment.setOnClickListener {
            Toast.makeText(view.context,"ဆေးညွန်းမှာယူ ခြင်း အောင်မြင်ပါသည်", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}