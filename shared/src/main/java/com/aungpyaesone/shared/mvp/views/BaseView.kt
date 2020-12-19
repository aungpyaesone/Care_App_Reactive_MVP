package com.aungpyaesone.shared.mvp.views

import androidx.appcompat.app.AlertDialog


interface BaseView {
    fun showErrorMessage(error: String)
    fun showLoading()
    fun hideLoading()
    fun showAlertDialog(): AlertDialog?
}