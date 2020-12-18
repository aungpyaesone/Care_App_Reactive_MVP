package com.aungpyaesone.patient.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.activities.ChatActivity
import com.aungpyaesone.patient.adapters.ConsultationAdapter
import com.aungpyaesone.patient.dialog.PrescriptionInfoDialogFragment
import com.aungpyaesone.patient.mvp.presenters.ConsultationPresenter
import com.aungpyaesone.patient.mvp.presenters.impls.ConsultationPresenterImpl
import com.aungpyaesone.patient.mvp.view.ConsultationView
import com.aungpyaesone.patient.views.view_pods.EmptyViewPod
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.google.gson.Gson
import com.aungpyaesone.shared.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_consultation.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConsultationFragment : BaseFragment(),ConsultationView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mPresenter : ConsultationPresenter
    private lateinit var mAdapter : ConsultationAdapter
    private lateinit var mEmptyView : EmptyViewPod

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
        return inflater.inflate(R.layout.fragment_consultation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setupViewPod()
        setupRecycler()

        mPresenter.onUiReady(this)
    }

    private fun setupViewPod() {
        mEmptyView = emptyView as EmptyViewPod
    }

    private fun setupRecycler() {
        mAdapter = ConsultationAdapter(mPresenter)
        rvConsultationView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = mAdapter
            setEmptyView(mEmptyView)
        }
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ConsultationPresenterImpl,ConsultationView>()
    }

    override fun showConsultationList(consultationList: List<ConsultationChatVO>) {
        mAdapter.setData(consultationList.asReversed())
    }

    override fun navigateToChatActivity(consultationChatVO: ConsultationChatVO) {
        val data=  Gson().toJson(consultationChatVO)
        startActivity(activity?.let { ChatActivity.newInstance(it,consultationChatVO.id)})
    }

    override fun showPrescriptionDialog(consultationChatVO: ConsultationChatVO) {
        val data=  Gson().toJson(consultationChatVO)
        consultationChatVO?.let {
            val dialog: PrescriptionInfoDialogFragment = PrescriptionInfoDialogFragment.newInstance(consultationChatVO.id,consultationChatVO.patient?.name,
                consultationChatVO.dateTime
            )
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "") }
        }
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
         * @return A new instance of fragment ConsultationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsultationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}