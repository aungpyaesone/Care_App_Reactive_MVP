
package com.aungpyaesone.shared.extensions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import com.aungpyaesone.shared.data.vos.*
import com.bumptech.glide.Glide
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

fun MutableMap<String,Any>?.convertToGeneralQuestionVO() : GeneralQuestionVO {
    val questionVO = GeneralQuestionVO()
    questionVO.sq_id = this?.get("id") as String
    questionVO.question = this["question"] as String
    questionVO.type = this["type"]as String
    return questionVO
}

fun MutableMap<String,Any>?.convertToChatMessage() : ChatMessageVO {
    val chatMessageVO = ChatMessageVO()
    chatMessageVO.messageText = this?.get("messageText") as String
    chatMessageVO.messageImage = this["messageImage"] as String
    chatMessageVO.sendBy = this["sendBy"] as SenderVO
    return chatMessageVO
}


fun MutableMap<String,Any>?.convertToPatientVO(): PatientVO{
    val patientVO = PatientVO()
    patientVO.id = this?.get("id") as String
    patientVO.name = this["name"] as String
    patientVO.photo = this["photo"] as String
    return patientVO
}

@SuppressLint("CheckResult")
fun Completable.dbOperationResult(onSuccess:(String)->Unit, onFailure:(String)->Unit){
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{
            doOnComplete {
                onSuccess("Database CRUD Success")
            }
            doOnError {
                onFailure("${it.message}")
            }
        }
}




