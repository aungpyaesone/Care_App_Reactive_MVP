package com.aungpyaesone.shared.data.models.impls

import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.network.auth.AuthManager
import com.aungpyaesone.shared.network.impls.AuthManagerImpls

object AuthenticationModelImpls : AuthenticationModel,BaseModel(){
    override var mAuthManager: AuthManager = AuthManagerImpls

    override fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.login(email,password,onSuccess = onSuccess,onFailure = onFailure)
    }

    override fun register(email: String, password: String, userName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.registerDoctor(email,password,userName,onSuccess,onFailure)
    }



}