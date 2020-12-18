package com.aungpyaesone.patient.views.view_pods

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.adapters.QuestionAnswerAdapter
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import kotlinx.android.synthetic.main.patient_info_viewpod.view.*

class PatientItemViewPod @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private lateinit var mQuestionAnswerListAdapter : QuestionAnswerAdapter
    private var mChatVO : ConsultationChatVO? = null
    private var mDelegate: Delegate? = null
    override fun onFinishInflate() {
        super.onFinishInflate()
        setupAdapter()
        setUpListener()

    }

    fun setDelegate(delegate:Delegate){
        mDelegate = delegate
    }

    private fun setUpListener() {
        tvShowMore.setOnClickListener {
            mDelegate?.onTapSeenPatientInfo(mChatVO)
        }
    }

    private fun setupAdapter() {
        mQuestionAnswerListAdapter = QuestionAnswerAdapter()
        rvQuestionAnswer.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = mQuestionAnswerListAdapter
        }
    }

    fun setData(consultationChatVO: ConsultationChatVO?){
        mChatVO = consultationChatVO
        var summaryList : MutableList<QuestionAnswerVO> = arrayListOf()
        consultationChatVO?.patient?.let {
            tvPName.text = it.name
            tvPHeight.text = it.height
            tvPDob.text = it.dob
            tvPbloodType.text = it.blood_type
            tvBloodPressure.text = it.blood_pressure
            tvPAllergic.text = it.allergic_medicine
            tvWeight.text = it.weight
        }
        summaryList.clear()
        for(item in 0..1){
            consultationChatVO?.caseSummary?.get(item)?.let { summaryList.add(it) }
        }
        mQuestionAnswerListAdapter.setData(summaryList)
    }

    interface Delegate{
        fun onTapSeenPatientInfo(consultationChatVO: ConsultationChatVO?)
    }
}