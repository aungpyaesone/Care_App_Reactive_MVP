package com.aungpyaesone.doctors.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.QuestionAnswerAdapter
import com.aungpyaesone.doctors.mvp.presenters.ChatPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.ChatPresenterImpl
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_patient_info_dialog.view.*
import java.text.DateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PatientInfoDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientInfoDialogFragment : DialogFragment() {
    private lateinit var mPresenter: ChatPresenter

    companion object {

        private const val KEY_CONSULATIONCHATVO = "cousulationChatVO"
        fun newInstance(cousulationChatVO: String): PatientInfoDialogFragment {
            val args = Bundle()
            args.putString(KEY_CONSULATIONCHATVO, cousulationChatVO)
            val fragment = PatientInfoDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patient_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(view: View) {
        var data = arguments?.getString(KEY_CONSULATIONCHATVO)
        val gson = Gson()
        var consultationChatVO = gson.fromJson(data, ConsultationChatVO::class.java)

        view.pname.text = " :  " + consultationChatVO.patient?.name
        view.pdateofBirth.text =  " :  " +consultationChatVO.patient?.dob
        view.pheight.text =  " :  " + consultationChatVO.patient?.height   + " ft"
        view.pbloodtype.text = " :  " + DateFormat.getDateInstance().format(consultationChatVO.dateTime?.toLong()).toString()
        view.pweight.text =  " :  " +consultationChatVO.patient?.blood_type
        view.pbloodpressure.text =  " :  " +consultationChatVO.patient?.blood_pressure + " mmHg"
        view.weight.text =  " :  " +consultationChatVO.patient?.weight
        view.pcomment.text =  " :  " + consultationChatVO.patient?.allergic_medicine

        view.rc_question_answer?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(ChatPresenterImpl::class.java)
        }
        var questionAnswerAdapter = QuestionAnswerAdapter()
        view.rc_question_answer?.adapter = questionAnswerAdapter
        view.rc_question_answer?.setHasFixedSize(false)

        consultationChatVO.caseSummary?.let{
            questionAnswerAdapter.setData(it)
        }
    }

    private fun setupClickListeners(view: View) {
        view.btn_close.setOnClickListener {
            dismiss()
        }
    }
}