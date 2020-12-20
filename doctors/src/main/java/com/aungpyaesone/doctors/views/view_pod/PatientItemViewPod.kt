package com.aungpyaesone.doctors.views.view_pod

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.adapters.QuestionAnswerAdapter
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import kotlinx.android.synthetic.main.patient_info_viewpod.view.*

class PatientItemViewPod @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private lateinit var mQuestionAnswerListAdapter : QuestionAnswerAdapter
    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setupAdapter()
        setUpListener()
    }

    private fun setUpListener() {
        tvShowAgain.setOnClickListener {
            mDelegate?.onTapSeenPatientInfo()
        }
    }

    private fun setupAdapter() {
        mQuestionAnswerListAdapter = QuestionAnswerAdapter()
        rvQuestionAnswer.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = mQuestionAnswerListAdapter
        }
    }

    fun setDelegate(delegate: Delegate){
        this.mDelegate = delegate
    }

    @SuppressLint("SetTextI18n")
    fun setData(patientVO: PatientVO?, questionAnswerList:List<QuestionAnswerVO>?){
        var summaryList : MutableList<QuestionAnswerVO> = arrayListOf()
        patientVO?.let {
            tvPName.text = patientVO.name
            tvPHeight.text = patientVO.height + " ft"
            tvPDob.text = patientVO.dob
            tvPbloodType.text = patientVO.blood_type
            tvBloodPressure.text = patientVO.blood_pressure + " mmHg"
            tvPAllergic.text = patientVO.allergic_medicine
            tvWeight.text = patientVO.weight + " lb"
        }
        summaryList.clear()
        for(item in 0..1){
            questionAnswerList?.get(item)?.let { summaryList.add(it) }
        }
        mQuestionAnswerListAdapter.setData(summaryList)
    }

    interface Delegate{
        fun onTapSeenPatientInfo()
    }
}