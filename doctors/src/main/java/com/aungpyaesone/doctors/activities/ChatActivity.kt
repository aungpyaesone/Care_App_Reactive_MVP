package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.ChatAdapter
import com.aungpyaesone.doctors.fragments.PatientInfoDialogFragment
import com.aungpyaesone.doctors.mvp.presenters.ChatPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.ChatPresenterImpl
import com.aungpyaesone.doctors.mvp.views.ChatView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.doctors.views.view_pod.PatientItemViewPod
import com.aungpyaesone.doctors.views.view_pod.PrescriptionViewPod
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.DateUtils
import com.aungpyaesone.shared.util.sharePreferenceDoctor
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseActivity(),ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var mChatAdapter: ChatAdapter
    private lateinit var mPatientInfoViewPod : PatientItemViewPod
    private lateinit var mPrescriptionViewPod : PrescriptionViewPod
    private var mPatientVO: PatientVO? = null
    private var mDoctorVO: DoctorVO? = null
    private var mChatId : String? = null
    private var mConsultationChatVO : ConsultationChatVO? = null

    companion object{
        const val DOCUMENT_ID = "document_id"
        fun newInstance(context: Context, documentId:String)= Intent(context,ChatActivity::class.java).apply {
            putExtra(DOCUMENT_ID,documentId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setUpPresenter()
        setupListener()
        setupViewPod()
        setupRecycler()
        mChatId = intent.getStringExtra(DOCUMENT_ID)
        mChatId?.let { mPresenter.onReady(it,this) }
    }

    private fun setupRecycler() {
        mChatAdapter = ChatAdapter()
        rvChatView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity,RecyclerView.VERTICAL,false)
            adapter = mChatAdapter

        }
    }

    private fun setupListener() {
        val messageVO = ChatMessageVO()
        val senderVO = SenderVO()
        ivSend.setOnClickListener {
            if(etMessage.text.toString().isNotEmpty())
            {
                mDoctorVO?.let {
                    senderVO.id = it.id
                    senderVO.photo = it.photo
                    senderVO.name = it.name
                    messageVO.messageText = etMessage.text.toString()
                    messageVO.sendAt = DateUtils.getDate(System.currentTimeMillis())
                    messageVO.sentBy = senderVO
                    SessionManager.put(it,sharePreferenceDoctor)
                    mChatId?.let{
                        mPresenter.onTapSendMessage(it,messageVO)
                        etMessage.text.clear()
                    }

                }

            }
        }

        btnPrescribe.setOnClickListener {
            mDoctorVO?.speciality?.let { data -> mPresenter.onTapGiveMedicine(data) }
        }

        btnQuestions.setOnClickListener {
            mChatId?.let {
                    it1 -> mPresenter.onTapGeneralMessageTemplate(it1) }
        }

        btnHistory.setOnClickListener {
            mChatId?.let {
                mPresenter.onTapNote()
            }
        }

    }


    private fun setUpPresenter() {
        mPresenter = getPresenter<ChatPresenterImpl,ChatView>()
    }

    private fun setupViewPod() {
        mPatientInfoViewPod = vpatientInfoViewPod as PatientItemViewPod
        mPrescriptionViewPod = prescriptionViewPod as PrescriptionViewPod
        mPatientInfoViewPod.setDelegate(mPresenter)

    }
    override fun showConsultationChat(consultationChatList: ConsultationChatVO) {
        scrollView.scrollTo(0,scrollView.bottom)
        mConsultationChatVO = consultationChatList
        bindToolbar(consultationChatList.patient)
        mDoctorVO = consultationChatList.doctor
        mPatientVO = consultationChatList.patient
        mPatientInfoViewPod.setData(consultationChatList.patient,consultationChatList.caseSummary)
        when(consultationChatList.status){
            true ->{
                sendTextLayout.visibility = View.GONE
            }
            else ->{
                sendTextLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun showAllChatMessage(chatMessageList: List<ChatMessageVO>) {
        scrollView.scrollTo(0,scrollView.bottom)
        mChatAdapter.setData(chatMessageList)
    }

    override fun navigateToPrescribeMedicineScreen(speciality: String) {
        val data = Gson().toJson(mConsultationChatVO)
        startActivity(PrescriptionMedicineActivity.newInstance(this,data))
        finish()
    }

    override fun navigateToGeneralQuestionTemplate(documentId: String) {
        startActivity(GeneralQuestionTemplate.newInstance(this,documentId))
    }

    override fun navigateToNoteActivity() {
        val data = Gson().toJson(mConsultationChatVO)
        startActivity(mChatId?.let { NotesActivity.newInstance(this, data) })
    }

    override fun showPrescriptionList(prescriptionList: List<PrescriptionVO>) {
        scrollView.scrollTo(0,scrollView.bottom)
        if(prescriptionList.isNotEmpty()){
            mPrescriptionViewPod.visibility = View.VISIBLE
            mDoctorVO?.photo?.let {
                mPrescriptionViewPod.setPrescriptionData(prescriptionList,it)
            } ?: kotlin.run {
                mPrescriptionViewPod.setPrescriptionData(prescriptionList,getDrawable(R.drawable.image_placeholder).toString())
            }
        }else
        {
            mPrescriptionViewPod.visibility = View.GONE
        }
    }

    override fun showPatientInfoDialog() {
        var data=  Gson().toJson(mConsultationChatVO)
        mConsultationChatVO?.let {
            val dialog: PatientInfoDialogFragment = PatientInfoDialogFragment.newInstance(data)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun bindToolbar(patientVO: PatientVO?) {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        patientVO?.let {
            tvToolbarTitle.text = it.name
            it.photo?.toUri()?.let { it1 -> ivProfile.load(it1,R.drawable.image_placeholder) }
        }

    }
}