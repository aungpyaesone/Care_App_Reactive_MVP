package com.aungpyaesone.patient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.SpecialQuestionAdapter
import com.aungpyaesone.patient.delegate.CaseSummaryDelegate
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.SummaryPresenterImpl
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.aungpyaesone.shared.fragments.BaseFragment
import com.aungpyaesone.shared.util.sharePreferencePatient
import kotlinx.android.synthetic.main.fragment_specialities_question.*

private const val ARG_PARAM1 = "param1"

class SpecialitiesQuestionFragment : BaseFragment(),SummaryView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var mSpeciality: String? = null

    private lateinit var mPresenter : SummaryPresenter
    private lateinit var mAdapter : SpecialQuestionAdapter
    private lateinit var mDelegate: CaseSummaryDelegate
    private  var questionAnswerList : ArrayList<QuestionAnswerVO> = arrayListOf()
    private var mPatientVO: PatientVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mSpeciality = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specialities_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupRecycler()
        setupListener()
        mPresenter.onUiReady(this)
        mSpeciality?.let { mPresenter.getSpecialQuestion(it,this) }
    }

    private fun setupListener() {
        btnStartConsultation.setOnClickListener {
            for(item in questionAnswerList)
            {
                Log.d("data",item.answer.toString())
            }

            mPatientVO?.let { it1 -> mPresenter.onTapStartConsultation(it1,questionAnswerList) }
            mDelegate.onTapStartConsultationButton()
        }
    }

    private fun setupRecycler() {
        mAdapter = SpecialQuestionAdapter(mPresenter)
        rvSpecialQuestion.apply {
            layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
            adapter = mAdapter
            // setEmptyView()
        }
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<SummaryPresenterImpl,SummaryView>()
    }

    override fun showSpecialQuestion(specialQuestionList: List<SpecialQuestionVO>) {
       mAdapter.setData(specialQuestionList)
       questionAnswerList.clear()
        for (item in specialQuestionList)
        {
            questionAnswerList.add(QuestionAnswerVO(item.id,item.question,""))
        }
        mAdapter.setQuestionAnswerList(questionAnswerList)
    }

    override fun navigateToChatScreen() {

    }

    override fun showQuestionAnswer(position: Int, questionAnswer: QuestionAnswerVO) {
        questionAnswerList[position] = questionAnswer
    }

    override fun showFirstLayout() {

    }

    override fun showSecondLayout() {

    }

    override fun showPatientVO(patientVO: PatientVO) {
        SessionManager.put(patientVO, sharePreferencePatient)
        mPatientVO = patientVO
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpecialitiesQuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(delegate: CaseSummaryDelegate,speciality:String?) =
            SpecialitiesQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, speciality)
                }
                mDelegate = delegate
            }
    }
}