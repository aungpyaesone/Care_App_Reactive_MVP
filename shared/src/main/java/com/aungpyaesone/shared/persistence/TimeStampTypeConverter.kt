package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TimeStampTypeConverter {
    @TypeConverter
    fun toString(dataList: Timestamp):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): Timestamp {
        val dataListType = object : TypeToken<Timestamp>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}