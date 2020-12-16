package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.AcceptRequestAdapter
import com.aungpyaesone.doctors.adapters.RequestAdapter
import com.aungpyaesone.doctors.fragments.PatientInfoDialogFragment
import com.aungpyaesone.doctors.fragments.PrescriptionInfoDialogFragment
import com.aungpyaesone.doctors.mvp.presenters.HomePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.HomePresenterImpl
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import com.aungpyaesone.shared.extensions.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),HomeView {

    private lateinit var mPresenter: HomePresenter
    private lateinit var mAdapter : RequestAdapter
    private lateinit var mAcceptAdapter: AcceptRequestAdapter
    private var doctorId:String?  = null
    companion object {
        fun newInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setUpRecycler()
        subscribeNoti()
        setupListener()
        mPresenter.onUiReady(this)

    }

    private fun setupListener() {
        ivProfile.setOnClickListener {
            startActivity(DoctorProfileActivity.newInstance(this))
        }
    }

    private fun setUpRecycler() {
        tvDoctorName.text = SessionManager.doctor_name
        mAdapter = RequestAdapter(mPresenter)
        rvRequest.apply{
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
            adapter = mAdapter
        }

        mAcceptAdapter = AcceptRequestAdapter(mPresenter)
        rvConsultNote.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = mAcceptAdapter
        }

        ivProfile.load(SessionManager.photo.toString().toUri(),R.drawable.image_placeholder)
    }

    override fun showConsultationList(consultationList: List<ConsultationRequestVO>) {
        mAdapter.setData(consultationList)
    }

    override fun showAcceptRequestList(consultationChat: List<ConsultationChatVO>) {
        if(consultationChat.isEmpty()){
            tvLabel.visibility = View.GONE
        }else{
            tvLabel.visibility = View.VISIBLE
        }
        mAcceptAdapter.setData(consultationChat)

    }

    private fun subscribeNoti() {
        Firebase.messaging.subscribeToTopic(SessionManager.speciality.toString())
            .addOnCompleteListener { task ->
                var msg = "success scribe"
                if (!task.isSuccessful) {
                    msg = "failed"
                }
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                Log.d("success",msg)
            }
    }

    override fun payConsultedPatientList(consultationList: List<ConsultedPatientVO>) {
        mAdapter.setConsultedList(consultationList)
    }

    override fun navigateToConfirmScreen(id: String) {
        startActivity(ConfirmActivity.newInstance(this,id))
    }

    override fun navigateToChatScreen(consultChatId: String) {
        startActivity(ChatActivity.newInstance(this,consultChatId))
    }

    override fun showChooseTimeDialog() {

    }

    override fun showPatientInfoDialog(consultationChatVO: ConsultationChatVO) {
        val data=  Gson().toJson(consultationChatVO)
        consultationChatVO?.let {
            val dialog: PatientInfoDialogFragment = PatientInfoDialogFragment.newInstance(data)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun showPrescriptionInfoDialog(consultationChatVO: ConsultationChatVO) {
        val data=  Gson().toJson(consultationChatVO)
        consultationChatVO?.let {
            val dialog: PrescriptionInfoDialogFragment = PrescriptionInfoDialogFragment.newInstance(consultationChatVO.id,consultationChatVO.patient?.name,
            consultationChatVO.dateTime)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun setupPresenter() {
        mPresenter = getPresenter<HomePresenterImpl,HomeView>()
    }
}