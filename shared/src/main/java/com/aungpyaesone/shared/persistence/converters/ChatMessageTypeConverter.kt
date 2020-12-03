package com.aungpyaesone.shared.persistence.converters

import androidx.room.TypeConverter
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ChatMessageTypeConverter {
    @TypeConverter
    fun toString(dataList: ArrayList<ChatMessageVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<ChatMessageVO> {
        val dataListType = object : TypeToken<ArrayList<ChatMessageVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}