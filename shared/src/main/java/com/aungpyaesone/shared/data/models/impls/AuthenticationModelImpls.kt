package com.aungpyaesone.shared.data.models.impls

import android.net.Uri
import com.aungpyaesone.shared.data.models.AuthenticationModel
import com.aungpyaesone.shared.data.models.BaseModel
import com.aungpyaesone.shared.network.CloudFireStoreImpls
import com.aungpyaesone.shared.network.auth.AuthManager
import com.aungpyaesone.shared.network.impls.AuthManagerImpls
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object AuthenticationModelImpls : AuthenticationModel,BaseModel(){
    override var mAuthManager: AuthManager = AuthManagerImpls
    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.login(email,password, onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun register(email: String, password: String, userName: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.registerUser(email,password,userName,onSuccess,onFailure)
    }

    override fun checkCurrentUser(onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mAuthManager.checkCurrentUser(onSuccess,onFailure)
    }


    override  fun updateProfile(
        photoUrl : String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        mFirebaseAuth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder().
        setPhotoUri( Uri.parse(photoUrl)).build())?.addOnCompleteListener {
                task -> if(task.isSuccessful)
        {   onSuccess() } else{  onFailure("Fail Profile Update")}
        }
    }


}