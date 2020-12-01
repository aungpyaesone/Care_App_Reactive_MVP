package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoutineConverter {
    @TypeConverter
    fun toString(dataList: RoutineVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): RoutineVO {
        val dataListType = object : TypeToken<RoutineVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}