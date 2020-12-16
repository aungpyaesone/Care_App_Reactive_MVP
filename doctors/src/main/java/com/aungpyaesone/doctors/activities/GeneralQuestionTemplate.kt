package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.GeneralQuestionAdapter
import com.aungpyaesone.doctors.mvp.presenters.QuestionTemplatePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.QuestionTemplatePresenterImpl
import com.aungpyaesone.doctors.mvp.views.QuestionTemplateView
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_general_question_template.*
import kotlinx.android.synthetic.main.activity_general_question_template.ivBack


class GeneralQuestionTemplate : BaseActivity(),QuestionTemplateView {
    private lateinit var mPresenter : QuestionTemplatePresenter
    private lateinit var mAdapter : GeneralQuestionAdapter

    companion object {
        const val DOCUMENT_ID = "document_id"
        fun newInstance(context: Context, documentId:String)= Intent(context,GeneralQuestionTemplate::class.java).apply {
            putExtra(DOCUMENT_ID,documentId)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_question_template)
        setUpPresenter()
        setUpRecycler()
        setupListener()
        intent.getStringExtra(DOCUMENT_ID)?.let { mPresenter.onReady(it,this) }
    }

    override fun showQuestionView(templateList: List<GeneralQuestionVO>) {
        mAdapter.setData(templateList)
    }

    override fun navigateToChatActivity() {
        val documentId = intent.getStringExtra(DOCUMENT_ID)
        documentId?.let{
            startActivity(ChatActivity.newInstance(this,it))
            finish()
        }

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun setupListener() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        tvToolbarTitle.text = getString(R.string.question_label)
    }

    private fun setUpRecycler() {
        mAdapter = GeneralQuestionAdapter(mPresenter)
        rvQuestionTemplate.apply{
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
            adapter = mAdapter
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<QuestionTemplatePresenterImpl, QuestionTemplateView>()
    }

}