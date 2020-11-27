package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.QuestionVO
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<QuestionVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<QuestionVO> {
        val dataListType = object : TypeToken<ArrayList<RoutineVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}