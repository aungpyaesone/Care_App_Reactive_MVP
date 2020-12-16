package com.padc.shared.activites

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.padc.shared.mvp.presenter.AbstractBasePresenter
import com.padc.shared.mvp.views.BaseView

abstract class BaseActivity : AppCompatActivity(),BaseView{

    companion object{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : AbstractBasePresenter<W>, reified W: BaseView> getPresenter(): T {
        val presenter = ViewModelProviders.of(this).get(T::class.java)
        if (this is W) presenter.initPresenter(this)
        return presenter
    }

    override fun showErrorMessage(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }

    /*fun showDialog(): Dialog{

    }*/
}