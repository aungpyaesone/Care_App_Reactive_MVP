package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.QuestionAnswerVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CaseSummaryTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<QuestionAnswerVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<QuestionAnswerVO> {
        val dataListType = object : TypeToken<ArrayList<QuestionAnswerVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}