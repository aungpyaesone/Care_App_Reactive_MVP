package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.CheckoutAdapter
import com.aungpyaesone.patient.adapters.ShippingAddressAdapter
import com.aungpyaesone.patient.dialog.CheckoutDialog
import com.aungpyaesone.patient.mvp.presenters.CheckoutPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.CheckOutPresenterImpl
import com.aungpyaesone.patient.mvp.view.CheckoutView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.activites.BaseActivity
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.util.sharePreferencePatient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.add_shipping_bottom_sheet.view.*
import kotlin.time.times

class CheckOutActivity : BaseActivity(), CheckoutView {
    private lateinit var mPresenter: CheckoutPresenter
    private lateinit var adapter: CheckoutAdapter
    private lateinit var shippingAdapter: ShippingAddressAdapter
    private lateinit var consultation_chat_id: String
    private lateinit var mConsultationChatVO: ConsultationChatVO
    private lateinit var prescriptionList : List<PrescriptionVO>
    private lateinit var totalPrice : String
    private lateinit var state : String
    private lateinit var township : String
    private lateinit var address: String
    private var previousPosition : Int = -1
    private lateinit var shippingList : List<String>
    companion object {
        const val PARM_CONSULTATION_CHAT_ID = "chat id"
        private const val ConsultationCHAT = "ConsultationCHAT"

        fun newIntent(context: Context, consultation_chat_id: String, consultationChatVO: String) : Intent {
            val intent = Intent(context, CheckOutActivity::class.java)
            intent.putExtra(PARM_CONSULTATION_CHAT_ID, consultation_chat_id)
            intent.putExtra(ConsultationCHAT, consultationChatVO)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        consultation_chat_id = intent.getStringExtra(PARM_CONSULTATION_CHAT_ID).toString()
        var data = intent?.getStringExtra(ConsultationCHAT)
        mConsultationChatVO = Gson().fromJson(data, ConsultationChatVO::class.java)
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
    private fun setUpActionListeners() {

        checkoutback.setOnClickListener {
            onBackPressed()
        }
        add_address.setOnClickListener {
          showAlertDialog()
           // showBottomSheetDialog()
        }
        btn_order.setOnClickListener {

            mConsultationChatVO?.let {
                mPresenter.onTapCheckout(prescriotionList = prescriptionList,
                    deliveryAddressVO = address,
                    doctorVO = it?.doctor,
                    patientVO = it?.patient,
                    total_price = totalPrice
                )
            }

        }
    }
    private fun setUpPresenter()
    {
        mPresenter = getPresenter<CheckOutPresenterImpl,CheckoutView>()
       // mPresenter.onUiReady(this, this)
        mPresenter.onUiReadyCheckout(consultation_chat_id ,this)
    }

    override fun displayPrescription(list: List<PrescriptionVO>) {
        if(list.isNotEmpty()) {
            prescriptionList= list
            adapter.setData(list.toMutableList())
            var totalamount : Int =0
            for( item in list)
            {
                totalamount += item.count?.toInt()?.let { item.price?.toInt()?.times(it) } ?: 0
            }
            total_amount.text = "${totalamount}"
            totalPrice =  "${totalamount} ကျပ်"

        }
    }

    override fun displayShippingAddress(list: List<String>) {
        if(list.isNotEmpty())
        {
            shippingList= list
            shippingAdapter.setData(list.toMutableList())
        }else{

        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.add_shipping_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        view.state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                state = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.township_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                township = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        view.btn_add.setOnClickListener {
            address= "${view.ed_address.text }  ၊ ${township} ၊ ${state} "
            var patientVO = SessionManager.get<PatientVO>(sharePreferencePatient)

            /***
             * notice this place
             */
         //   patientVO?.address.add(address)

            mPresenter.onTapAddShipping(patientVO)
            dialog.dismiss()
        }
        view.shipping_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun displayConfirmDialog(list: List<PrescriptionVO>, address: String, total_price: String) {
        var data=  Gson().toJson(prescriptionList)
        val dialog: CheckoutDialog = CheckoutDialog.newInstance(data,address,total_price)
        dialog.show(supportFragmentManager, "")
    }

    override fun selectedShippingAddress(mAddress: String,mpreviousPosition: Int) {

        address= mAddress
        btn_order.visibility = View.VISIBLE
        previousPosition = mpreviousPosition
        shippingAdapter = ShippingAddressAdapter(mPresenter, previousPosition)
        shippingList?.let {
            shippingAdapter.setData(shippingList.toMutableList())
            address_rc?.adapter = shippingAdapter
        }

    }

    override fun showErrorMessage(error: String) {

    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    private fun setUpRecyclerView() {
        prescription_rct?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CheckoutAdapter(mPresenter)
        prescription_rct?.adapter = adapter
        prescription_rct?.setHasFixedSize(false)

        address_rc?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        shippingAdapter = ShippingAddressAdapter(mPresenter, previousPosition)
        address_rc?.adapter = shippingAdapter
        address_rc?.setHasFixedSize(false)

    }
}