package com.aungpyaesone.doctors.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.AcceptRequestAdapter
import com.aungpyaesone.doctors.adapters.RequestAdapter
import com.aungpyaesone.doctors.fragments.NotesInfoDialog
import com.aungpyaesone.doctors.fragments.PatientInfoDialogFragment
import com.aungpyaesone.doctors.fragments.PrescriptionInfoDialogFragment
import com.aungpyaesone.doctors.mvp.presenters.HomePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.HomePresenterImpl
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.doctors.views.view_pod.EmptyViewPod
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import com.aungpyaesone.shared.extensions.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_pone_dialog.view.*

class MainActivity : BaseActivity(),HomeView {

    private lateinit var mPresenter: HomePresenter
    private lateinit var mAdapter : RequestAdapter
    private lateinit var mAcceptAdapter: AcceptRequestAdapter
    private lateinit var mEmptyViewPod: EmptyViewPod
    private var doctorId:String?  = null
    companion object {
        fun newInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setupViewPod()
        setUpRecycler()
        subscribeNoti()
        setupListener()
        mPresenter.onUiReady(this)

    }

    private fun setupViewPod() {
        mEmptyViewPod = emptyView as EmptyViewPod
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
            setEmptyView(mEmptyViewPod)
        }

        ivProfile.load(SessionManager.photo.toString().toUri(),R.drawable.image_placeholder)
    }

    override fun showConsultationList(consultationList: List<ConsultationRequestVO>) {
        if(consultationList.isNotEmpty()){
            rvRequest.visibility = View.VISIBLE
            mAdapter.setData(consultationList)
        }else{
            rvRequest.visibility = View.GONE
        }
    }

    override fun showAcceptRequestList(consultationChat: List<ConsultationChatVO>) {
        if(consultationChat.isEmpty()){
            tvLabel.visibility = View.GONE
           // emptyView.visibility = View.VISIBLE
        }else{
            tvLabel.visibility = View.VISIBLE
           // emptyView.visibility = View.GONE
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

    override fun showChooseTimeDialog(consultationRequestVO: ConsultationRequestVO) {
        val view = layoutInflater.inflate(R.layout.post_pone_dialog, null)
        val dialog = this?.let { Dialog(it) }
        val timePicker = view?.findViewById<TimePicker>(R.id.timePicker)
        var msg : String =""
        timePicker?.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

            val h = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            // display format of time
            msg = " $h : $min $am_pm"

        }
        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.confirm.setOnClickListener {
            msg?.let{mPresenter.onTapPostponeTime(view.context,it,consultationRequestVO)}
            dialog?.dismiss()
        }
        dialog?.show()
    }

    override fun showNotesDialog(consultationChatVO: ConsultationChatVO) {
        val data=  Gson().toJson(consultationChatVO)
        if(consultationChatVO.note.isNullOrBlank()){
           showErrorMessage(getString(R.string.no_notes))
        }else{
            consultationChatVO.let {
                val dialog: NotesInfoDialog = NotesInfoDialog.newInstance(data)
                dialog.show(supportFragmentManager, "")
            }
        }

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