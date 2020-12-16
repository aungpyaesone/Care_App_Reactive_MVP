package com.aungpyaesone.patient.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.activities.ChatActivity
import com.aungpyaesone.patient.adapters.AcceptRequestAdapter
import com.aungpyaesone.patient.adapters.RecentlyDoctorAdapter
import com.aungpyaesone.patient.adapters.SpecialitiesAdapter
import com.aungpyaesone.patient.dialog.ConfirmDialogFragment
import com.aungpyaesone.patient.dialog.ConfirmDialogFragment.Companion.BUNDLE_ID
import com.aungpyaesone.patient.dialog.ConfirmDialogFragment.Companion.BUNDLE_IMAGE
import com.aungpyaesone.patient.dialog.ConfirmDialogFragment.Companion.BUNDLE_NAME
import com.aungpyaesone.patient.mvp.presenters.HomePresenter
import com.aungpyaesone.patient.mvp.presenters.impls.HomePresenterImpl
import com.aungpyaesone.patient.mvp.view.HomeView
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import com.aungpyaesone.shared.util.checkSpeciality
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.padc.shared.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(),HomeView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mPresenter: HomePresenter
    private lateinit var mAdapter: SpecialitiesAdapter
    private lateinit var mRecentlyDoctorAdapter: RecentlyDoctorAdapter
    private lateinit var mAcceptDoctorAdapter: AcceptRequestAdapter
    // private lateinit var mConsultationViewPod: ConsultationViewPod

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpViewPod()
        setUpRecycler()
        mPresenter.onUiReady(this)
    }

    private fun setUpRecycler() {
        mAdapter = SpecialitiesAdapter(mPresenter)
        mRecentlyDoctorAdapter = RecentlyDoctorAdapter(mPresenter)
        mAcceptDoctorAdapter = AcceptRequestAdapter(mPresenter)
        rvSpecialities.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = mAdapter
            // setEmptyView()
        }
        rvRecentDoctor.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = mRecentlyDoctorAdapter
        }

        rvAcceptConsultationView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = mAcceptDoctorAdapter
        }
    }

    private fun setUpViewPod() {
        /*mConsultationViewPod = acceptConsultationView as ConsultationViewPod
        mConsultationViewPod.setViewPodData(DoctorVO())
        mConsultationViewPod.setDelegate(mPresenter)*/
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<HomePresenterImpl, HomeView>()
    }

    override fun showSpecialitiesList(specialitiesList: List<SpecialitiesVO>) {
        mAdapter.setData(specialitiesList)
    }

    override fun showRecentlyConsultedDoctor(recentlyDoctorList: List<RecentDoctorVO>) {
        if (recentlyDoctorList.isEmpty()) {
            recentConsultation.visibility = View.GONE
            rvRecentDoctor.visibility = View.GONE
        } else {
            recentConsultation.visibility = View.VISIBLE
            rvRecentDoctor.visibility = View.VISIBLE
            mRecentlyDoctorAdapter.setData(recentlyDoctorList.toMutableList())
        }

    }

    override fun showConfirmationDialog(specialitiesVO: SpecialitiesVO) {
        SessionManager.flag = true
        val confirmDialogFragment = ConfirmDialogFragment.newFragment()
        val bundle = Bundle()
        SessionManager.speciality = checkSpeciality(specialitiesVO.name)
        bundle.putString(BUNDLE_NAME, specialitiesVO.name)
        bundle.putString(BUNDLE_IMAGE, specialitiesVO.photo)
        bundle.putString(BUNDLE_ID, specialitiesVO.id)
        confirmDialogFragment.arguments = bundle
        activity?.supportFragmentManager?.let {
            confirmDialogFragment.show(
                it, ""
            )
        }
    }

    override fun showConConfirmDialog(recentDoctorVO: RecentDoctorVO) {
        SessionManager.flag = true
        val confirmDialogFragment = ConfirmDialogFragment.newFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_ID, recentDoctorVO.speciality)
        bundle.putString(BUNDLE_NAME, recentDoctorVO.speciality_myanmar)
        confirmDialogFragment.arguments = bundle
        activity?.supportFragmentManager?.let {
            confirmDialogFragment.show(
                it, ""
            )
        }

    }

    override fun showAcceptDoctorList(consultationRequestList: List<ConsultationRequestVO>) {
        mAcceptDoctorAdapter.setData(consultationRequestList)
    }

    override fun navigateToCaseSummary() {

    }

    override fun navigateToChatActivity(consultationRequestVO: ConsultationRequestVO) {
        val data = Gson().toJson(consultationRequestVO)
        startActivity(activity?.let { ChatActivity.newInstance(it, consultationRequestVO.id) })
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String?, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}