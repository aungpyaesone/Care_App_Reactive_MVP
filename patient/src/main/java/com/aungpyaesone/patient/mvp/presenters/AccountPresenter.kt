package com.aungpyaesone.patient.mvp.presenters

import com.aungpyaesone.patient.mvp.view.AccountView
import com.aungpyaesone.shared.mvp.presenter.BasePresenter

interface AccountPresenter : BasePresenter<AccountView> {
    fun onTapLogout()
    fun onTapChangePassword()
    fun onTapEdit()
}