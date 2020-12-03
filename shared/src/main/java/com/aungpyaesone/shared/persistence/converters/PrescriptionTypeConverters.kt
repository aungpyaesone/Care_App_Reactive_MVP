package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrescriptionTypeConverters {
    @TypeConverter
    fun toString(dataList: ArrayList<PrescriptionVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<PrescriptionVO> {
        val dataListType = object : TypeToken<ArrayList<RoutineVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}
