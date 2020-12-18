package com.aungpyaesone.shared.mvp.views

interface BaseView {
    fun showErrorMessage(error: String)
    fun showLoading()
    fun hideLoading()
}