package com.padc.shared.mvp.views

interface BaseView {
    fun showErrorMessage(error: String)
    fun showLoading()
    fun hideLoading()
}