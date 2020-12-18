package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.QuestionAnswerAdapter
import com.aungpyaesone.patient.mvp.presenters.ConfirmPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.ConfirmPresenterImpl
import com.aungpyaesone.patient.mvp.view.ConfirmView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.util.sharePreferencePatient
import com.aungpyaesone.shared.util.sharePreferenceQandA
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.activity_confirmation.ivBack
import kotlinx.android.synthetic.main.activity_confirmation.tvToolbarTitle

class ConfirmationActivity : BaseActivity(), ConfirmView {

    private lateinit var mPresenter : ConfirmPresenter
    private lateinit var mAdapter : QuestionAnswerAdapter
    companion object{
        fun newInstance(context: Context)= Intent(context,ConfirmationActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        setupPresenter()
        setupRecycler()
        loadData()
        setupListener()
    }

    private fun setupListener() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        tvToolbarTitle.text = getString(R.string.toolbar_title)
        btnStartConsultation.setOnClickListener {
            mPresenter.onTapConfirmStartConsultation(this)
        }
    }

    private fun setupRecycler() {
        mAdapter = QuestionAnswerAdapter()
        rvQuestionAnswer.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = mAdapter
        }
    }

    private fun loadData() {
        val patientData = SessionManager.get<PatientVO>(sharePreferencePatient) ?: PatientVO()
        val questionAnswerList = SessionManager.getList(sharePreferenceQandA)
        tvPName.text = patientData.name
        tvPDob.text = patientData.dob
        tvPHeight.text = patientData.height
        tvPbloodType.text = patientData.blood_type
        tvPAllergic.text = patientData.allergic_medicine
        tvWeight.text = patientData.weight
        tvBloodPressure.text = patientData.blood_pressure
        mAdapter.setData(questionAnswerList ?: arrayListOf())
    }

    override fun navigateToHomeScreen() {
        startActivity(HomeActivity.newInstance(this))
        this.finishAffinity()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun setupPresenter() {
        mPresenter = getPresenter<ConfirmPresenterImpl,ConfirmView>()
    }
}