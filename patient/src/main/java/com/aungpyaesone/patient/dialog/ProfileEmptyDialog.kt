package com.aungpyaesone.patient.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.activities.EditProfileActivity
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.util.sharePreferencePatient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.profile_empty_dialog_layout.*
import kotlinx.android.synthetic.main.profile_empty_dialog_layout.view.*

class ProfileEmptyDialog : DialogFragment() {

    companion object {
        const val BUNDLE_NAME = "name"
        const val BUNDLE_IMAGE = "photo"
        const val BUNDLE_ID = "id"
        fun newFragment(): ProfileEmptyDialog {
            return ProfileEmptyDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_empty_dialog_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btnAddInfo.setOnClickListener {
            activity?.let {
                val data = SessionManager.get<PatientVO>(sharePreferencePatient)
                val patient = Gson().toJson(data)
                startActivity(EditProfileActivity.newInstance(it,patient))
            }
        }

        ivclose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val params = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }




}