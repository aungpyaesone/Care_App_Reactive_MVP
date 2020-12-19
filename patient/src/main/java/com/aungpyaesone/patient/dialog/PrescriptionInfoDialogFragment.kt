package com.aungpyaesone.patient.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.PrescriptionInfoAdapter
import com.aungpyaesone.patient.mvp.presenters.PrescriptonInfoPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.PrescriptonInfoPresenterImpl
import com.aungpyaesone.patient.mvp.view.PrescriptionInfoView
import com.aungpyaesone.patient.views.view_pods.EmptyViewPod
import com.aungpyaesone.shared.data.models.PatientModel
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_prescription_info_dialog.*
import kotlinx.android.synthetic.main.fragment_prescription_info_dialog.view.*
import java.text.DateFormat

class PrescriptionInfoDialogFragment : DialogFragment(), PrescriptionInfoView {
    private lateinit var mPresenter: PrescriptonInfoPresenter
    private val mPatientModel = PatientModelImpls

    private lateinit var mAdapter: PrescriptionInfoAdapter
    private var chatVO: ConsultationChatVO? = null

    companion object {
        private const val CHAT_VO = "chat_vo"
        fun newInstance(chat: String): PrescriptionInfoDialogFragment {
            val args = Bundle()
            args.putString(CHAT_VO, chat)
            val fragment = PrescriptionInfoDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prescription_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupViePod()
        setupView(view)
        setupClickListeners(view)
        mPresenter.onUiReady(this)
        //  mPresenter.onUiReadyForPrescription(chat_id.toString(),this)

        chatVO?.id?.let { mPatientModel.getPrescriptionFromApi(it, onSuccess = {}, onFailure = {}) }
        mPatientModel.getPrescriptionFromDb().observe(this, Observer {
            it?.let {
                mAdapter.setData(it)
            }
        })
    }

    private fun setupViePod() {
        // mViewPod = emptyView as EmptyViewPod
    }

    private fun setupPresenter() {
        mAdapter = PrescriptionInfoAdapter()
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(PrescriptonInfoPresenterImpl::class.java)
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun setupView(view: View) {
        val data = arguments?.getString(CHAT_VO)
        chatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        view.rc_medicinelist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.rc_medicinelist.adapter = mAdapter
        view.rc_medicinelist.setHasFixedSize(false)
    }

    private fun setupClickListeners(view: View) {
        view.btn_close.setOnClickListener {
            dismiss()
        }
    }

    override fun displayPrescriptionList(prescription_list: List<PrescriptionVO>) {
        mAdapter.setData(prescription_list)
    }

    override fun showErrorMessage(error: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showAlertDialog(): AlertDialog? {
        return null
    }
}