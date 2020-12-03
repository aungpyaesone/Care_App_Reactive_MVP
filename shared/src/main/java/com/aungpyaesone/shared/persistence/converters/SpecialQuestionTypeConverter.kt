package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpecialQuestionTypeConverter {
    @TypeConverter
    fun toString(dataList: SpecialQuestionVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): SpecialQuestionVO {
        val dataListType = object : TypeToken<SpecialQuestionVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}