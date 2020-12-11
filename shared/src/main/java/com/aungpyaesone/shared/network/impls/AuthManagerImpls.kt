package com.aungpyaesone.shared.network.impls

import android.graphics.Bitmap
import android.util.Log
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.network.auth.AuthManager
import com.aungpyaesone.shared.util.EN_ERROR_MESSAGE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage

object AuthManagerImpls : AuthManager{
    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override fun registerUser(email: String, password: String, userName: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                mFirebaseAuth.currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build()
                )
                mFirebaseAuth.currentUser?.uid?.let { userId -> onSuccess(userId) }
            } else {
                onFailure(it.exception?.message ?: "Please check your internet connection")
            }
        }
    }

    override fun registerNewPatient(email: String, password: String, userName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

    }

    override fun login(email: String, password: String, onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                Log.d("login","success login")
                mFirebaseAuth.currentUser?.uid?.let { it1 -> onSuccess(it1) }
            } else {
                onFailure(it.exception?.message ?: EN_ERROR_MESSAGE)
            }
        }
    }

    override fun getUserProfile(): DoctorVO {
        TODO("Not yet implemented")
    }

    override fun checkCurrentUser(onSuccess: (userId: String) -> Unit, onFailure: (String) -> Unit) {
        if(mFirebaseAuth.currentUser != null){
            onSuccess(mFirebaseAuth.currentUser!!.uid)
        }else{
            onFailure("sign in again")
        }
    }

    override fun updateProfileUrl(photoUrl: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun uploadProfileUrl(bitmap: Bitmap, onSuccess: (imgUrl: String) -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

}