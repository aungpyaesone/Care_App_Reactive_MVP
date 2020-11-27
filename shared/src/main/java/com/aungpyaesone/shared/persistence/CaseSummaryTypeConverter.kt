package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.CaseSummaryVO
import com.aungpyaesone.shared.data.vos.QuestionVO
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CaseSummaryTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<CaseSummaryVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<CaseSummaryVO> {
        val dataListType = object : TypeToken<ArrayList<CaseSummaryVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}