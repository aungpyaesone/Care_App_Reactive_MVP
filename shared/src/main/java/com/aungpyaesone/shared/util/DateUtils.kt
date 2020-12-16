package com.aungpyaesone.shared.util

import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class DateUtils {
    fun getDayAgo(dayAgo:Int): Date{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -dayAgo)
        return calendar.time
    }


    companion object{
        fun day() : ArrayList<String>{
            val dayList = arrayListOf<String>()
            for(item in 1..31){
                dayList.add(item.toString())
            }
            return dayList
        }
        fun year(): ArrayList<String>{
            val yearList = arrayListOf<String>()
            for(item in 1990..2020)
            {
                yearList.add(item.toString())
            }
            return yearList
        }

        fun getDate(millisecond:Long):String{
            return DateFormat.getTimeInstance().format(Date(millisecond))
        }
    }



    enum class Months {
        Jan,Feb,Mar, Apri, May, Jun, July, Aug, Sept, Oct, Nov, Dec
    }


}