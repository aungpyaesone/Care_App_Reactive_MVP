package com.aungpyaesone.doctors.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.QuestionAnswerAdapter
import com.aungpyaesone.doctors.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.ConfirmPresenterImpl
import com.aungpyaesone.doctors.mvp.views.ConfirmView
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : BaseActivity(),ConfirmView {

    private lateinit var mPresenter: ConfirmPresenter
    private lateinit var mAdapter : QuestionAnswerAdapter

    companion object{
        const val DOCUMENT_ID = "document_id"
        fun newInstance(context: Context,documentId:String)= Intent(context,ConfirmActivity::class.java).apply {
            putExtra(DOCUMENT_ID,documentId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        setUpPresenter()
        setUpRecycler()
        setupListener()
        intent.getStringExtra(DOCUMENT_ID)?.let { mPresenter.onUIReady(it,this) }
    }

    private fun setupListener() {
        btnStatConsultation.setOnClickListener {
            mPresenter.onTapStartConsultation(this)
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpRecycler() {
      mAdapter = QuestionAnswerAdapter()
      rvPatientSummary.apply {
          layoutManager = LinearLayoutManager(this@ConfirmActivity,RecyclerView.VERTICAL,false)
          adapter = mAdapter
      }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ConfirmPresenterImpl,ConfirmView>()
    }

    override fun showPatientInfo(consultationRequestVO: ConsultationRequestVO) {
        bindData(consultationRequestVO.patient)
        consultationRequestVO.case_summary?.toMutableList()?.let { mAdapter.setData(it) }
    }

    override fun navigateToChatActivity(documentId: String) {
        startActivity(ChatActivity.newInstance(this,documentId))
        finish()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(patientVO: PatientVO?){

        patientVO?.photo?.toUri()?.let { ivPatientProfile.load(it,R.drawable.image_placeholder) }
        tvPName.text = patientVO?.name
        tvPDob.text = patientVO?.dob
        tvPHeight.text = patientVO?.height + " ft"
        tvPbloodType.text = patientVO?.blood_type
        tvPAllergic.text = patientVO?.allergic_medicine
        tvWeight.text = patientVO?.weight + " lb"
        tvBloodPressure.text = patientVO?.blood_pressure
    }
}