package com.aungpyaesone.patient.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.activities.EditProfileActivity
import com.aungpyaesone.patient.activities.LoginActivity
import com.aungpyaesone.patient.dialog.ProfileEmptyDialog
import com.aungpyaesone.patient.mvp.presenters.AccountPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.AccountPresenterImpl
import com.aungpyaesone.patient.mvp.view.AccountView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.extensions.load
import com.google.gson.Gson
import com.aungpyaesone.shared.fragments.BaseFragment
import com.aungpyaesone.shared.util.sharePreferencePatient
import kotlinx.android.synthetic.main.fragment_account.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : BaseFragment(), AccountView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mPresenter: AccountPresenter
    private var mPatientVO: PatientVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        setupListener()
        mPresenter.onUiReady(lifecycleOwner = this)
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<AccountPresenterImpl, AccountView>()
    }

    private fun setupListener() {
        tvLogout.setOnClickListener {
            mPresenter.onTapLogout()
        }
        tvChangePassword.setOnClickListener {
            mPresenter.onTapChangePassword()
        }

        ivEditProfile.setOnClickListener {
            mPresenter.onTapEdit()
        }
    }

    override fun showAccount(patientVO: PatientVO) {
        mPatientVO = patientVO
        mPatientVO.let {
            tvPName.text = patientVO.name
            tvPhone.text = patientVO.phone
            patientVO.photo?.toUri()?.let { ivDoctorProfile.load(it, R.drawable.image_placeholder) }
            tvDob.text = patientVO.dob ?: ""
            tvBlood.text = patientVO.blood_type ?: ""
            tvHeight.text = patientVO.height ?: ""
            tvAllergic.text = patientVO.allergic_medicine ?: ""
            tvAddress.text = patientVO.address ?: ""
            if (patientVO.dob.isNullOrBlank() && patientVO.blood_type.isNullOrBlank()
                && patientVO.height.isNullOrBlank() && patientVO.allergic_medicine.isNullOrBlank()
                && patientVO.address.isNullOrBlank()
            ) {
                val data = Gson().toJson(mPatientVO)
                val emptyDialogFragment = ProfileEmptyDialog.newInstance(data)
                activity?.supportFragmentManager?.let {
                    emptyDialogFragment.show(
                        it, "")
                }
            }
        }

    }

    override fun showStatusDialog() {
        Toast.makeText(
            context,
            "this function is not available in this version",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()


    }

    override fun logoutView() {
        AlertDialog.Builder(context)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, which ->
                    context?.startActivity(activity?.let {
                        LoginActivity.newInstance(it)
                    })
                    activity?.finishAffinity()
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }

    override fun navigateToEditProfileScreen() {
        val data = Gson().toJson(mPatientVO)
        startActivity(activity?.let { EditProfileActivity.newInstance(it, data) })
    }


    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun showAlertDialog(): androidx.appcompat.app.AlertDialog? {
        return null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}