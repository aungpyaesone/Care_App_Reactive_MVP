package com.aungpyaesone.shared.data.models

import android.content.Context
import com.aungpyaesone.shared.network.ApiService
import com.aungpyaesone.shared.persistence.db.CareDatabase
import com.aungpyaesone.shared.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


abstract class BaseModel {
    protected var mApiService: ApiService
    protected lateinit var mTheDB : CareDatabase
    fun initDatabase(context: Context){
        mTheDB = CareDatabase.getDBInstance(context)
    }
    init {

        val logging  = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

            val mOkHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build()

            mApiService = retrofit.create(ApiService::class.java)
    }
}