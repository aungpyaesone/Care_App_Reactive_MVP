package com.aungpyaesone.shared.data.models

import android.content.Context
import com.aungpyaesone.shared.persistence.db.CareDatabase

abstract class BaseModel {
    protected lateinit var mTheDB : CareDatabase


    fun initDatabase(context: Context){
        mTheDB = CareDatabase.getDBInstance(context)
    }
}