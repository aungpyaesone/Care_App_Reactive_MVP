package com.aungpyaesone.shared.activites

import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.aungpyaesone.shared.R
import com.google.android.material.snackbar.Snackbar
import com.aungpyaesone.shared.mvp.presenter.AbstractBasePresenter
import com.aungpyaesone.shared.mvp.views.BaseView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity : AppCompatActivity(), BaseView {

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : AbstractBasePresenter<W>, reified W : BaseView> getPresenter(): T {
        val presenter = ViewModelProviders.of(this).get(T::class.java)
        if (this is W) presenter.initPresenter(this)
        return presenter
    }

    override fun showErrorMessage(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_SHORT).show()
    }


    override fun showAlertDialog(): AlertDialog?{
        MaterialAlertDialogBuilder(this).apply {
            setTitle(title)
            setMessage(getString(R.string.unavailable))
            setPositiveButton(
                getString(R.string.positive)
            ) { dialog, which -> dialog?.dismiss() }
            setCancelable(true)
        }.apply {
            return create().apply {
                show()
            }
        }
    }


    /*fun showDialog(): Dialog{

    }*/
}