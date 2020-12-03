package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.PatientVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DoctorVOTypeConverter {
        @TypeConverter
        fun toString(dataList: DoctorVO):String{
            return Gson().toJson(dataList)
        }

        @TypeConverter
        fun toList(ListJsonStr:String): DoctorVO {
            val dataListType = object : TypeToken<DoctorVO>(){}.type
            return Gson().fromJson(ListJsonStr,dataListType)
        }
}