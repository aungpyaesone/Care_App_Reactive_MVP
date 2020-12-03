package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.aungpyaesone.shared.data.vos.SenderVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SenderTypeConverter {
    @TypeConverter
    fun toString(dataList: SenderVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): SenderVO {
        val dataListType = object : TypeToken<SenderVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}