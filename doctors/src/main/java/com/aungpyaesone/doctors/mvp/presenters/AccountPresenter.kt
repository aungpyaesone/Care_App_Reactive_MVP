package com.aungpyaesone.doctors.mvp.presenters

import com.aungpyaesone.doctors.mvp.views.AccountView
import com.padc.shared.mvp.presenter.BasePresenter

interface AccountPresenter : BasePresenter<AccountView> {
    fun onTapLogout()
    fun onTapChangePassword()
    fun onTapEdit()
}