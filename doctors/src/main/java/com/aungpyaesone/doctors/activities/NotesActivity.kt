package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.mvp.presenters.NotePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.NotePresenterImpl
import com.aungpyaesone.doctors.mvp.views.NoteView
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.google.gson.Gson
import com.aungpyaesone.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_notes.ivBack
import java.text.DateFormat

class NotesActivity : BaseActivity(),NoteView {

    private lateinit var mPresenter : NotePresenter
    private var mNodeId : String? = null
    private var mChatVO : ConsultationChatVO? = null
    companion object{
        const val CHAT_VO = "chat_vo"
        fun newInstance(context: Context, chatVO:String)= Intent(context,NotesActivity::class.java).apply {
            putExtra(CHAT_VO,chatVO)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setUpPresenter()
        setupListener()
        var data = intent?.getStringExtra(CHAT_VO)
        mChatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        mPresenter.onUiReady(this)
        bindData(mChatVO)
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<NotePresenterImpl,NoteView>()
    }

    fun bindData(consultationChatVO: ConsultationChatVO?){
        ivBack.setOnClickListener {
            onBackPressed()
        }

        tvPName.text = consultationChatVO?.patient?.name
        tvPDob.text = consultationChatVO?.patient?.dob
        tvPHeight.text = DateFormat.getDateInstance().format(consultationChatVO?.dateTime?.toLong()).toString()
    }

    private fun setupListener() {
        btnSave.setOnClickListener {
            if(etNote.text.toString().isNotEmpty()){
                mChatVO?.note = etNote.text?.toString()
                mChatVO?.let { it1 -> mPresenter.onTapWriteNote(it1.id,it1)
                }
            }

        }
    }

    override fun navigateToChatActivity() {
        finish()
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }
}