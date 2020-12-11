package com.aungpyaesone.patient.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.adapters.PagerAdapter
import com.aungpyaesone.patient.delegate.CaseSummaryDelegate
import com.aungpyaesone.patient.mvp.presenters.SummaryPresenter
import com.padc.shared.activites.BaseActivity
import kotlinx.android.synthetic.main.activity_case_summary.*

class CaseSummaryActivity : BaseActivity(),CaseSummaryDelegate {
    private lateinit var mPresenter : SummaryPresenter
    private var mSpeciality:String? = ""

    companion object{
        const val SPECIALITIES = "SPECIALITIES"
        fun newInstance(context: Context,specialities:String) :Intent {
            val intent = Intent(context,CaseSummaryActivity::class.java)
            intent.putExtra(SPECIALITIES,specialities)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_summary)
        setupPager()
        setupListener()
    }


    private fun setupListener() {
        ivBack.setOnClickListener {
            onBackPressed()
        }
        tvToolbarTitle.text = getString(R.string.toolbar_title)
    }

    private fun setupPager() {
        pager.adapter = PagerAdapter(supportFragmentManager, this,"",intent.getStringExtra(SPECIALITIES))
        stepper_indicator.setViewPager(pager)
        stepper_indicator.addOnStepClickListener {
            pager.setCurrentItem(it,true)
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onTapContinueCallback() {
        pager.currentItem = 1
      //  mPresenter.onTapContinue()
    }

    override fun onTapStartConsultationButton() {
        startActivity(ConfirmationActivity.newInstance(this))
    }

}