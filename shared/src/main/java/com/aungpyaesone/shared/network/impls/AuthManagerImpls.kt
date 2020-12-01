package com.aungpyaesone.shared.network.impls

import android.graphics.Bitmap
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.network.auth.AuthManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage

object AuthManagerImpls : AuthManager{
    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override fun registerDoctor(email: String, password: String, userName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                mFirebaseAuth.currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build()
                )
                onSuccess()
            } else {
                onFailure(it.exception?.message ?: "Please check your internet connection")
            }
        }
    }

    override fun registerNewPatient(email: String, password: String, userName: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                onSuccess()
            } else {
                onFailure(it.exception?.message ?: "please check internet connection")
            }
        }
    }

    override fun getUserProfile(): DoctorVO {
        TODO("Not yet implemented")
    }

    override fun updateProfileUrl(photoUrl: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun uploadProfileUrl(bitmap: Bitmap, onSuccess: (imgUrl: String) -> Unit, onFailure: (String) -> Unit) {
        TODO("Not yet implemented")
    }

}