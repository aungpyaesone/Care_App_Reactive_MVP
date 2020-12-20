package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.mvp.presenters.SeePatientInfoPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.SeePatientInfoPresenterImpl
import com.aungpyaesone.patient.mvp.view.SeePatientInfoView
import com.aungpyaesone.patient.views.view_pods.PatientItemViewPodTwo
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.google.gson.Gson
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_see_patient_info.*

class SeePatientInfoActivity : BaseActivity(),SeePatientInfoView {

    private lateinit var mPresenter: SeePatientInfoPresenter
    private var mConsultationChatVO: ConsultationChatVO? = null
    private lateinit var mViewPod: PatientItemViewPodTwo

    companion object{
        const val CHATVO = "chatvo"
        fun newInstance(context: Context,chatVO:String)= Intent(context,SeePatientInfoActivity::class.java).apply {
            putExtra(CHATVO,chatVO)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_patient_info)
        setUpPresenter()
        setUpViewPod()
        setupListener()

        val data = intent.getStringExtra(CHATVO)
        mConsultationChatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        bindData(mConsultationChatVO)
    }

    private fun setUpViewPod() {
        mViewPod = seePatientInfoViwpod as PatientItemViewPodTwo
    }

    private fun setupListener() {

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<SeePatientInfoPresenterImpl,SeePatientInfoView>()
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    private fun bindData(chatvo: ConsultationChatVO?){
        mViewPod.setData(chatvo?.patient,chatvo?.caseSummary)
       // chatvo?.patient?.photo?.toUri()?.let { ivPatientProfile.load(it,R.drawable.image_placeholder) }

    }
}