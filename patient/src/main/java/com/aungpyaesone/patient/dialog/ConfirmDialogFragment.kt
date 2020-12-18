package com.aungpyaesone.patient.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.activities.CaseSummaryActivity
import com.aungpyaesone.patient.mvp.presenters.HomePresenter
import com.aungpyaesone.patient.mvp.presenters.impls.HomePresenterImpl
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import kotlinx.android.synthetic.main.consaulation_comfirm_dialog.*

class ConfirmDialogFragment : DialogFragment() {
    private lateinit var mPresenter: HomePresenter

    companion object {
        const val BUNDLE_NAME = "name"
        const val BUNDLE_IMAGE = "photo"
        const val BUNDLE_ID = "id"
        fun newFragment(): ConfirmDialogFragment {
            return ConfirmDialogFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.consaulation_comfirm_dialog, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupListener()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupListener() {
        val speciality = arguments?.getString(BUNDLE_ID)
        val name = arguments?.getString(BUNDLE_NAME)
        val body = activity?.getString(R.string.confirm_body_text)
        tvBody.text = "$name $body"
        btnPositive.setOnClickListener {
            activity?.startActivity(activity?.applicationContext?.let { context ->
                speciality?.let { speciality ->
                    CaseSummaryActivity.newInstance(
                        context, speciality
                    )
                }
            })
            dismiss()
        }
        btnNegative.setOnClickListener {
            dismiss()
        }

    }

    private fun setupPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(HomePresenterImpl::class.java)
        }
    }
}