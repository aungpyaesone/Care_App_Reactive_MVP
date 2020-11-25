package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoutineConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<RoutineVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<RoutineVO> {
        val dataListType = object : TypeToken<ArrayList<RoutineVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}