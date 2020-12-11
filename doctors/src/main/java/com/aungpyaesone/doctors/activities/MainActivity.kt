package com.aungpyaesone.doctors.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.adapters.AcceptRequestAdapter
import com.aungpyaesone.doctors.adapters.RequestAdapter
import com.aungpyaesone.doctors.mvp.presenters.HomePresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.HomePresenterImpl
import com.aungpyaesone.doctors.mvp.views.HomeView
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),HomeView {

    private lateinit var mPresenter: HomePresenter
    private lateinit var mAdapter : RequestAdapter
    private lateinit var mAcceptAdapter: AcceptRequestAdapter
    companion object {
        fun newInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        setUpRecycler()
        mPresenter.onUiReady(this)
    }

    private fun setUpRecycler() {
        tvDoctorName.text = SessionManager.doctor_name
        mAdapter = RequestAdapter(mPresenter)
        rvRequest.apply{
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
            adapter = mAdapter
        }

        mAcceptAdapter = AcceptRequestAdapter(mPresenter)
        rvConsultNote.apply {
            layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)
            adapter = mAcceptAdapter
        }
    }

    override fun showConsultationList(consultationList: List<ConsultationRequestVO>) {
       mAdapter.setData(consultationList)
    }

    override fun showAcceptRequestList(consultationList: List<ConsultationRequestVO>) {
        mAcceptAdapter.setData(consultationList)
    }

    override fun navigateToConfirmScreen(id: String) {
        startActivity(ConfirmActivity.newInstance(this,id))
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun setupPresenter() {
        mPresenter = getPresenter<HomePresenterImpl,HomeView>()
    }
}