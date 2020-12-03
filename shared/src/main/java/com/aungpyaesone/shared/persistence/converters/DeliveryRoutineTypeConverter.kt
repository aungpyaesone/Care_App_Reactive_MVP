package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.DeliveryRoutineVO
import com.aungpyaesone.shared.data.vos.RoutineVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DeliveryRoutineTypeConverter {
    @TypeConverter
    fun toString(dataList: DeliveryRoutineVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): DeliveryRoutineVO {
        val dataListType = object : TypeToken<DeliveryRoutineVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}