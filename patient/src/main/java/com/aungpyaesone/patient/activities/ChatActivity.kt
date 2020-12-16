package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.ChatAdapter
import com.aungpyaesone.patient.mvp.presenters.ChatPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.ChatPresenterImpl
import com.aungpyaesone.patient.mvp.view.ChatView
import com.aungpyaesone.patient.views.view_pods.PatientItemViewPod
import com.aungpyaesone.patient.views.view_pods.PrescriptionViewPod
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.extensions.load
import com.aungpyaesone.shared.util.DateUtils
import com.google.gson.Gson
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.ivBack
import kotlinx.android.synthetic.main.activity_chat.tvToolbarTitle
import kotlinx.android.synthetic.main.fragment_general_question.*

class ChatActivity : BaseActivity(),ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var mPatientInfoViewPod : PatientItemViewPod
    private lateinit var mPrescriptionViewPod : PrescriptionViewPod
    private lateinit var mChatAdapter : ChatAdapter
    private var mPatientVO: PatientVO? = null
    private var mDoctorVO : DoctorVO? = null
    private var mChatId : String? = null

    companion object{
        const val CHAT_ID = "chat_id"
        fun newInstance(context: Context, documentId:String)= Intent(context,ChatActivity::class.java).apply {
            putExtra(CHAT_ID,documentId)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupPresenter()
        setupListener()
        setupViewPod()
        setupRecycler()
        mChatId = intent.getStringExtra(CHAT_ID)
        mChatId?.let { mPresenter.onReady(it,this) }
    }

    private fun setupRecycler() {
        mChatAdapter = ChatAdapter()
        rvChatView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL,false)
            adapter = mChatAdapter
            scrollToPosition(0)
        }
    }

    private fun setupListener() {
        val messageVO = ChatMessageVO()
        val senderVO = SenderVO()
        ivSend.setOnClickListener {
            if(etMessage.text.isNotEmpty())
            {
                mPatientVO?.let {
                    senderVO.id = it.id
                    senderVO.photo = it.photo
                    senderVO.name = it.name
                    messageVO.messageText = etMessage.text.toString()
                    messageVO.sendAt = DateUtils.getDate(System.currentTimeMillis())
                    messageVO.sentBy = senderVO
                    mChatId?.let{
                    mPresenter.onTapSendMessage(it,messageVO)
                    etMessage.text.clear()
                    }

                }

            }
        }
    }
    private fun setupViewPod() {
        mPatientInfoViewPod = vpatientInfoViewPod as PatientItemViewPod
        mPrescriptionViewPod = prescriptionViewPod as PrescriptionViewPod

    }
    private fun setupPresenter() {
        mPresenter = getPresenter<ChatPresenterImpl,ChatView>()
    }

    override fun showConsultationChat(consultationChatList: ConsultationChatVO) {
        bindToolbar(consultationChatList.doctor)
        mPatientVO = consultationChatList.patient
        mDoctorVO = consultationChatList.doctor
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

    private fun bindToolbar(doctorVO: DoctorVO?) {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        doctorVO?.let {
            tvToolbarTitle.text = it.name
            it.photo?.toUri()?.let { it1 -> ivProfile.load(it1,R.drawable.image_placeholder) }
        }

    }

    override fun showAllChatMessage(chatMessageList: List<ChatMessageVO>) {
        mChatAdapter.setData(chatMessageList)
    }

    override fun navigateToPrescribeMedicineScreen() {

    }

    override fun showPrescriptionList(prescriptionList: List<PrescriptionVO>) {
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

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}