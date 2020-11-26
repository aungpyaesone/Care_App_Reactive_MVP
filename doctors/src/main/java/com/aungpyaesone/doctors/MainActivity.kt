package com.aungpyaesone.doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aungpyaesone.doctors.mvp.presenters.RegisterPresenter
import com.aungpyaesone.doctors.mvp.presenters.impls.RegisterPresenterImpls
import com.aungpyaesone.doctors.mvp.views.RegisterView
import com.padc.shared.activites.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var mPresenter : RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPresenter()
        mPresenter.onUiReady(this)
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    private fun setupPresenter() {
        mPresenter = getPresenter<RegisterPresenterImpls,RegisterView>()
    }
}