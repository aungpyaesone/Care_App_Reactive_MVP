package com.aungpyaesone.patient.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.AcceptRequestAdapter
import com.aungpyaesone.patient.delegate.CaseSummaryDelegate
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.SummaryPresenterImpl
import com.aungpyaesone.patient.mvp.view.SummaryView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.aungpyaesone.shared.util.DateUtils
import com.google.android.material.snackbar.Snackbar
import com.padc.shared.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_general_question.*
import kotlinx.android.synthetic.main.one_time_view_pod.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GeneralQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GeneralQuestionFragment : BaseFragment(),SummaryView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var layoutStatus: Boolean = true

    private lateinit var mPresenter : SummaryPresenter
    private lateinit var mListener : CaseSummaryDelegate

    private var email: String? = null
    private var year: String? = null
    private var month: String? = null
    private var day: String? = null
    private var bloodType: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        init()
        setupListener()
        setupSpinnerListener()
        mPresenter.getGeneralQuestion("",this)
    }

    private fun setupSpinnerListener() {
        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                day = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spBloodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                bloodType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun init(){
        if(SessionManager.patient_height.isNullOrEmpty()){
            showFirstLayout()
            spDay.apply {
                val mSpinAdapter = activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,DateUtils.day()) }
                mSpinAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = mSpinAdapter
            }
            spMonth.apply {
                val mSpMonthAdapter = activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,DateUtils.Months.values()) }
                mSpMonthAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = mSpMonthAdapter
            }
            spYear.apply {
                val spYearAdapter = activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,DateUtils.year()) }
                spYearAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = spYearAdapter
            }
        }
        else {
            showSecondLayout()
            tvPName.text = SessionManager.patient_name
            tvPDob.text = SessionManager.patient_dateOfBirth
            tvPHeight.text = SessionManager.patient_height
            tvPbloodType.text = SessionManager.patient_blood_type
            tvPAllergic.text = SessionManager.comment
        }

    }

    private fun setupListener() {
        btnContinue.setOnClickListener {
            val height = etHeight.text.toString()
            val comment = etComment.text.toString()
            val weight = etWeight.text.toString()
            val bloodPressure = etBloodPressure.text.toString()

            when(layoutStatus){
                true ->{
                    if (height.isNotEmpty() && comment.isNotEmpty() && weight.isNotEmpty() && bloodPressure.isNotEmpty())
                    {
                        SessionManager.patient_dateOfBirth = "$day/$month/$year"
                        SessionManager.patient_height = "$height ft"
                        SessionManager.patient_blood_type = "$bloodType"
                        SessionManager.comment = comment
                        SessionManager.patient_height = "$height ft"
                        SessionManager.bloodPressure = "$bloodPressure mmHg"
                        SessionManager.weight = "$weight lb"
                        mListener.onTapContinueCallback()
                    } else {
                        val snackBar = Snackbar.make(
                                it, resources.getString(R.string.form_validate),
                                Snackbar.LENGTH_SHORT
                        ).setAction(resources.getString(R.string.know), null)
                        snackBar.setActionTextColor(Color.BLACK)
                        val snackBarView = snackBar.view
                        snackBarView.setBackgroundColor(Color.WHITE)
                        val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                        textView.setTextColor(Color.BLACK)
                        snackBar.show()
                    }
                }
                else->{
                    if (weight.isNotEmpty() && bloodPressure.isNotEmpty()) {
                        SessionManager.bloodPressure = bloodPressure
                        SessionManager.weight = weight
                        mListener.onTapContinueCallback()
                    } else {
                        val snackBar = Snackbar.make(
                                it, resources.getString(R.string.form_validate),
                                Snackbar.LENGTH_SHORT
                        ).setAction(resources.getString(R.string.know), null)
                        snackBar.setActionTextColor(Color.BLACK)
                        val snackBarView = snackBar.view
                        snackBarView.setBackgroundColor(Color.WHITE)
                        val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                        textView.setTextColor(Color.BLACK)
                        snackBar.show()
                    }
                }
            }

        }
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<SummaryPresenterImpl,SummaryView>()
    }

    override fun showSpecialQuestion(specialQuestionList: List<SpecialQuestionVO>) {

    }
    override fun navigateToChatScreen() {
    }

    override fun showQuestionAnswer(position: Int, questionAnswer: QuestionAnswerVO) {

    }

    override fun showFirstLayout() {
        firstLayout.visibility = View.VISIBLE
        secondLayout.visibility = View.GONE
        etComment.visibility = View.VISIBLE
        tvComment.visibility = View.VISIBLE
        layoutStatus = true
    }

    override fun showSecondLayout() {
       secondLayout.visibility = View.VISIBLE
       firstLayout.visibility = View.GONE
       etComment.visibility = View.GONE
       tvComment.visibility = View.GONE
       layoutStatus = false
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
         * @return A new instance of fragment GeneralQuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(listener:CaseSummaryDelegate,oneTime:String?) =
            GeneralQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
                this.mListener = listener
            }
    }
}
