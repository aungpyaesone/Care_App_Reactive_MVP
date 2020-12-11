package com.aungpyaesone.shared.data.models.impls

import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.network.auth.AuthManager
import com.aungpyaesone.shared.network.impls.AuthManagerImpls

object AuthenticationModelImpls : AuthenticationModel,BaseModel(){
    override var mAuthManager: AuthManager = AuthManagerImpls

    override fun login(email: String, password: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.login(email,password, onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun register(email: String, password: String, userName: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.registerUser(email,password,userName,onSuccess,onFailure)
    }

    override fun checkCurrentUser(onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.checkCurrentUser(onSuccess,onFailure)
    }


}