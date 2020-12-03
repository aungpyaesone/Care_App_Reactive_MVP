package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.PatientVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PatientVOTypeConverter {
    @TypeConverter
    fun toString(dataList:PatientVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): PatientVO {
        val dataListType = object : TypeToken<PatientVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}