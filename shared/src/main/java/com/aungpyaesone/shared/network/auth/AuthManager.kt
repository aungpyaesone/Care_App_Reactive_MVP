package com.aungpyaesone.shared.network.auth

import android.graphics.Bitmap
import com.aungpyaesone.shared.data.vos.DoctorVO

interface AuthManager {
    fun registerUser(email:String, password:String, userName:String, onSuccess:(userId:String)->Unit,
                     onFailure:(String)->Unit)

    fun registerNewPatient(email: String,password: String,userName: String,onSuccess: () -> Unit,
    onFailure: (String) -> Unit)

    fun login(email: String,password: String,onSuccess: (userId:String) -> Unit,onFailure: (String) -> Unit)

    fun getUserProfile(): DoctorVO

    fun checkCurrentUser(onSuccess: (userId:String) -> Unit,onFailure: (String) -> Unit)
    fun updateProfileUrl(photoUrl: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit)
    fun uploadProfileUrl(bitmap: Bitmap, onSuccess: (imgUrl:String) -> Unit, onFailure: (String) -> Unit)
}