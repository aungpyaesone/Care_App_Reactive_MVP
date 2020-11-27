package com.aungpyaesone.shared.persistence

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.aungpyaesone.shared.data.vos.SpecialitiesCollectionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpecialitiesCollectionTypeConverter {
    @TypeConverter
    fun toString(dataList: SpecialitiesCollectionVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): SpecialitiesCollectionVO {
        val dataListType = object : TypeToken<SpecialitiesCollectionVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}