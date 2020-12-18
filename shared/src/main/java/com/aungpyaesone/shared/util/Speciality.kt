package com.aungpyaesone.shared.util

import android.content.Context
import com.aungpyaesone.shared.R
import com.aungpyaesone.shared.data.vos.DataVO
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.NotificationVO
import com.aungpyaesone.shared.data.vos.PatientVO


fun checkSpeciality(str: String?): String {
    return when (str) {
        "cardiologist" -> {
            "နှလုံးအထူးကု"
        }
        "children" -> {
            "ကလေးအထူးကု"
        }
        "dentist" -> {
            "သွားဘက်ဆိုင်ရာ"
        }
        "ear,nose" -> {
            "နား,နှာခေါင်းလည်းချောင်းဆိုင်ရာ"
        }
        "eye" -> {
            "မျက်စိအထူးကု"
        }
        "general physician" -> {
            "အထွေထွေရောဂါဆိုင်ရာ"
        }
        "internal" -> {
            "အသည်း ကျောက်ကပ်ဆိုင်ရာ"
        }
        "neurologist" -> {
            "အာရုံကြောအထူးကု"
        }
        "nutritionsts" -> {
            "အဟာရဆိုင်ရာ"
        }
        "bostetricians" -> {
            "သားဖွားမီးယပ်အထူးကု"
        }
        "reproduction" -> {
            "မျိုးပွားခြင်းဆိုင်ရာ"
        }
        "skin specialist" -> {
            "အရေပြားဆိုင်ရာ"
        }
        else -> {
            ""
        }
    }
}

fun checkMyanToEng(str: String?) :String {
    return when (str) {
        "နှလုံးအထူးကု" -> {
            "cardiologist"
        }
        "ကလေးအထူးကု" -> {
            "children"
        }
        "သွားဘက်ဆိုင်ရာ" -> {
            "dentist"
        }
        "နား,နှာခေါင်းလည်းချောင်းဆိုင်ရာ" -> {
            "ear,nose"
        }
        "မျက်စိအထူးကု" -> {
            "eye"
        }
        "အထွေထွေရောဂါဆိုင်ရာ" -> {
            "general physician"
        }
        "အသည်း ကျောက်ကပ်ဆိုင်ရာ" -> {
            "internal"
        }
        "အာရုံကြောအထူးကု" -> {
            "neurologist"
        }
        "အဟာရဆိုင်ရာ" -> {
            "nutritionsts"
        }
        "သားဖွားမီးယပ်အထူးကု" -> {
            "bostetricians"
        }
        "မျိုးပွားခြင်းဆိုင်ရာ" -> {
            "reproduction"
        }
        "အရေပြားဆိုင်ရာ" -> {
            "skin specialist"
        }
        else -> {
            ""
        }
    }
}

fun prepareNotification(context:Context,to:String?,data:PatientVO):NotificationVO{
    val notificationVO = NotificationVO()
    val dataVO = DataVO()
    notificationVO.to = to
    dataVO.title = context.getString(R.string.noti_title)
    dataVO.body = "${data.name}${context.getString(R.string.noti_body_for_doctor)}"
    dataVO.id = data.id
    notificationVO.data = dataVO
    return notificationVO
}

fun prepareNotification(context:Context,to:String?,data:DoctorVO?,str:String):NotificationVO{
    val notificationVO = NotificationVO()
    val dataVO = DataVO()
    notificationVO.to = to
    dataVO.title = context.getString(R.string.noti_title)
    dataVO.body = "${data?.name}${context.getString(R.string.noti_body_for_patient)}"
    dataVO.id = data?.id
    notificationVO.data = dataVO
    return notificationVO
}


