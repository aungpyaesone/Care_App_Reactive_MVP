package com.padc.shared.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.padc.shared.mvp.presenter.AbstractBasePresenter
import com.padc.shared.mvp.views.BaseView

abstract class BaseFragment: Fragment(),BaseView {

    inline fun <reified T : AbstractBasePresenter<W>, reified W: BaseView> getPresenter(): T {
        val presenter = ViewModelProviders.of(this).get(T::class.java)
        if (this is W) presenter.initPresenter(this)
        return presenter

    }

    override fun showErrorMessage(error: String) {
        activity?.window?.decorView?.let { Snackbar.make(it, error, Snackbar.LENGTH_LONG).show() }
    }
}