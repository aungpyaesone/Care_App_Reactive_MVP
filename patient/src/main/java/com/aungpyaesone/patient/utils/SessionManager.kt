package com.aungpyaesone.patient.utils

import android.content.Context
import android.content.SharedPreferences
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.aungpyaesone.shared.util.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object SessionManager {
    private const val NAME = sharePreferencePatient
    private const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun logout(){
        preferences.edit().clear().apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var speciality : String?
        get() = preferences.getString(sharePreferenceSpeciality, "")
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(sharePreferenceSpeciality, value)
        }

    var photo : String?
        get() = preferences.getString(sharePhoto, "")
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(sharePhoto, value)
        }

    var created_date : String?
        get() = preferences.getString(sharecreatedDate, "")
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(sharecreatedDate, value)
        }
    var phone : String?
        get() = preferences.getString(sharephone, "")
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(sharephone, value)
        }

    var address : String?
        get() = preferences.getString(shareAddress, "")
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(shareAddress, value)
        }


        var flag : Boolean
        get() = preferences.getBoolean(sharePreferenceFlag, false)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(sharePreferenceFlag,value)
        }

            var login_status: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(sharePreferenceLoginStatus, false)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(sharePreferenceLoginStatus, value)
        }

    var patient_name: String?

        get() = preferences.getString(sharePreferencePatientName, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencePatientName, value)
        }

    var patient_email: String?

        get() = preferences.getString(sharePreferencePatientEmail, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencePatientEmail, value)
        }

    var patient_id: String?

        get() = preferences.getString(sharePreferencePatientID, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencePatientID, value)
        }

    var patient_device_id: String?

        get() = preferences.getString(sharePreferencePatientDeviceID, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencePatientDeviceID, value)
        }

    var patient_blood_type: String?

        get() = preferences.getString(sharePreferencepatientBloodType, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencepatientBloodType, value)
        }
    var patient_dateOfBirth: String?

        get() = preferences.getString(sharePreferencepatientDob, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencepatientDob, value)
        }

    var patient_height: String?

        get() = preferences.getString(sharePreferencepatientHeight, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencepatientHeight, value)
        }

    var bloodPressure: String?

        get() = preferences.getString(sharePreferencebloodPressure, "")

        set(value) = preferences.edit {
            it.putString(sharePreferencebloodPressure, value)
        }

    var comment: String?

        get() = preferences.getString(sharedPreferencesComment, "")

        set(value) = preferences.edit {
            it.putString(sharedPreferencesComment, value)
        }

    var weight: String?

        get() = preferences.getString(sharedPreferencesWeight, "")

        set(value) = preferences.edit {
            it.putString(sharedPreferencesWeight, value)
        }

    fun <T> put(data:T,key: String){
        val jsonString = GsonBuilder().create().toJson(data)
        preferences.edit().putString(key,jsonString).apply()
    }

    inline fun <reified T> get(key:String):T?{
        val value = preferences.getString(key,null)
        return GsonBuilder().create().fromJson(value,T::class.java)
    }

    fun putList(data:List<QuestionAnswerVO>,key: String){
        val jsonString = GsonBuilder().create().toJson(data)
        preferences.edit().putString(key,jsonString).apply()
    }

    fun getList(key:String):List<QuestionAnswerVO>?{
        val value = preferences.getString(key,null)
        //GsonBuilder().create().fromJson(value,T::class.java)
        val type = object : TypeToken<List<QuestionAnswerVO>>() {}.type
        return Gson().fromJson(value,type)
    }

}