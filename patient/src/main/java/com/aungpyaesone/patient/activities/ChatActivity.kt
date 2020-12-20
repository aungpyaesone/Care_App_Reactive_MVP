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
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : BaseActivity(),ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var mPatientInfoViewPod : PatientItemViewPod
    private lateinit var mPrescriptionViewPod : PrescriptionViewPod
    private lateinit var mChatAdapter : ChatAdapter
    private var mPatientVO: PatientVO? = null
    private var mDoctorVO : DoctorVO? = null
    private var finishStatus: Boolean? = false
    private var chatId: String? = null
    private var mConsultationChatVO : ConsultationChatVO? = null

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
        chatId = intent.getStringExtra(CHAT_ID)
    //    mConsultationChatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        chatId?.let { mPresenter.onReady(it,this) }
    }

    private fun setupRecycler() {
        mChatAdapter = ChatAdapter()
        rvChatView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL,false)
            adapter = mChatAdapter
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
                    mConsultationChatVO?.id?.let{
                    mPresenter.onTapSendMessage(it,messageVO)
                    etMessage.text.clear()
                    }

                }

            }
        }

        ivAttachment.setOnClickListener{
            showAlertDialog()
        }
    }
    private fun setupViewPod() {
        mPatientInfoViewPod = vpatientInfoViewPod as PatientItemViewPod
        mPrescriptionViewPod = prescriptionViewPod as PrescriptionViewPod
        mPatientInfoViewPod.setDelegate(mPresenter)
        mPrescriptionViewPod.setDelegate(mPresenter)

    }
    private fun setupPresenter() {
        mPresenter = getPresenter<ChatPresenterImpl,ChatView>()
    }

    override fun showConsultationChat(consultationChatList: ConsultationChatVO) {
        scrollView.scrollTo(0,scrollView.getChildAt(0).height)
        mConsultationChatVO = consultationChatList
        bindToolbar(consultationChatList.doctor)
        mPatientVO = consultationChatList.patient
        mDoctorVO = consultationChatList.doctor
        mPatientInfoViewPod.setData(consultationChatList)
        finishStatus = consultationChatList.status

        when(consultationChatList.status){
            true ->{
                sendTextLayout.visibility = View.GONE
                mPrescriptionViewPod.visibility = View.VISIBLE
            }
            else ->{
                sendTextLayout.visibility = View.VISIBLE
                mPrescriptionViewPod.visibility = View.GONE
            }
        }
        scrollView.parent.requestChildFocus(scrollView,scrollView)

    }

    private fun bindToolbar(doctorVO: DoctorVO?) {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        doctorVO?.let {
            tvDoctorName.text = it.name
            tvSpeciality.text = it.speciality_myanmar
            it.photo?.toUri()?.let { it1 -> ivProfile.load(it1,R.drawable.image_placeholder) }
        }

    }

    override fun showAllChatMessage(chatMessageList: List<ChatMessageVO>) {
        scrollView.scrollTo(0,scrollView.getChildAt(0).height)
        mChatAdapter.setData(chatMessageList)
        scrollView.parent.requestChildFocus(scrollView,scrollView)
    }

    override fun navigateToPrescribeMedicineScreen() {
        showErrorMessage("This function is not available in this version")
    }

    override fun showPrescriptionList(prescriptionList: List<PrescriptionVO>) {
        if(prescriptionList.isNotEmpty()){
            mPrescriptionViewPod.visibility = View.VISIBLE
            mDoctorVO?.photo?.let {
                mConsultationChatVO?.id?.let { it1 ->
                    mPrescriptionViewPod.setPrescriptionData(prescriptionList, it,
                        it1
                    )
                }
            } ?: kotlin.run {
                mConsultationChatVO?.id?.let {
                    mPrescriptionViewPod.setPrescriptionData(
                        prescriptionList,
                        getDrawable(R.drawable.image_placeholder).toString(),
                        it
                    )
                }
            }
        }else
        {
            mPrescriptionViewPod.visibility = View.GONE
        }


    }

    override fun navigateToSeePatientInfoScreen(consultationChatVO: ConsultationChatVO?) {
        val data = Gson().toJson(consultationChatVO)
        mConsultationChatVO = consultationChatVO
        startActivity(SeePatientInfoActivity.newInstance(this,data))
    }

    override fun navigateToCheckOutScreen(chatId: String) {
        val data = Gson().toJson(mConsultationChatVO)
        startActivity(CheckOutActivity.newIntent(this,chatId,data))
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        showProgressDialog()
    }

}