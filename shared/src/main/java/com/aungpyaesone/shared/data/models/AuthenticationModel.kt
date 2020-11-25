package com.aungpyaesone.shared.data.models

import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.network.auth.AuthManager

interface AuthenticationModel {
    var mAuthManager: AuthManager
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(
            email: String,
            password: String,
            userName: String,
            onSuccess: () -> Unit,
            onFailure: (String) -> Unit
    )


}