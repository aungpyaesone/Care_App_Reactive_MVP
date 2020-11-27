
package com.aungpyaesone.shared.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.bumptech.glide.Glide
import io.reactivex.Completable
import java.io.ByteArrayInputStream


fun String.convertToBitMap():Bitmap?{
    return try{
        val byte = Base64.decode(this,Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(byte))
        bitmap
    }catch (e: Exception){
        e.message
        null
    }
}

fun ImageView.load(uri: Uri,placeHolder : Int){
    Glide.with(context)
        .asBitmap()
        .load(uri)
        .centerCrop()
        .placeholder(placeHolder)
        .into(this)
}

fun MutableMap<String,Any>?.convertToDoctorVO(): DoctorVO{
    val doctorVO = DoctorVO()
    doctorVO.id = this?.get("id") as String
    doctorVO.name = this["name"] as String
    doctorVO.photo = this["photo"] as String
    doctorVO.biography = this["biography"] as String
    doctorVO.degree = this["degree"] as String
    doctorVO.experience = this["experience"]as String
    doctorVO.address = this["address"] as String
    doctorVO.speciality = this["speciality"] as String
    doctorVO.email = this["email"] as String
    return doctorVO
}

fun MutableMap<String,Any>?.convertToPatientVO(): PatientVO{
    val patientVO = PatientVO()
    patientVO.id = this?.get("id") as String
    patientVO.name = this["name"] as String
    patientVO.photo = this["photo"] as String
    patientVO.address = this["address"] as String
    patientVO.gender = this["gender"] as String
    patientVO.dob = this["dob"]as String
    patientVO.age = this["age"] as String
    patientVO.phone = this["phone"] as String
    patientVO.email = this["email"] as String
    return patientVO
}

fun <T>Completable.insertToDb(data:T,onSuccess:(String)->Unit,onFailure:(String)->Unit){

}




