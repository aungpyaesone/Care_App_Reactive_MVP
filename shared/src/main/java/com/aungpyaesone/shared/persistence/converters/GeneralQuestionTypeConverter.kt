package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GeneralQuestionTypeConverter {
    @TypeConverter
    fun toString(dataList: GeneralQuestionVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): GeneralQuestionVO {
        val dataListType = object : TypeToken<GeneralQuestionVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}