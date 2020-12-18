package com.aungpyaesone.doctors.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aungpyaesone.doctors.R
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notes_dialog_layouts.*
import kotlinx.android.synthetic.main.notes_dialog_layouts.tvPDob
import kotlinx.android.synthetic.main.notes_dialog_layouts.tvPHeight
import kotlinx.android.synthetic.main.notes_dialog_layouts.tvPName
import java.text.DateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoutineDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesInfoDialog : DialogFragment() {
    private var mConsultationChatVO : ConsultationChatVO? = null

    companion object {

        private const val KEY_CONSULATIONCHATVO = "cousulationChatVO"
        fun newInstance(cousulationChatVO: String): NotesInfoDialog {
            val args = Bundle()
            args.putString(KEY_CONSULATIONCHATVO, cousulationChatVO)
            val fragment = NotesInfoDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_dialog_layouts, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupListener()
        val data = arguments?.getString(KEY_CONSULATIONCHATVO)
        mConsultationChatVO = Gson().fromJson(data,ConsultationChatVO::class.java)
        mConsultationChatVO?.let {
            bindData(it)
        }
    }

    private fun setupListener() {
       btnDismiss.setOnClickListener {
           dismiss()
       }
    }

    private fun setupPresenter() {

    }

    fun bindData(consultationChatVO: ConsultationChatVO?){
        tvPName.text = consultationChatVO?.patient?.name
        tvPDob.text = consultationChatVO?.patient?.dob
        tvPHeight.text = DateFormat.getDateInstance().format(consultationChatVO?.dateTime?.toLong()).toString()
        etNote.text = consultationChatVO?.note
    }
}