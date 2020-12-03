package com.aungpyaesone.patient

import android.app.Application
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls

class CareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    CoreModelImpls.initDatabase(applicationContext)
    }
}