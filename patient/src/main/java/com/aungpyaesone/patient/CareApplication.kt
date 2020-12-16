package com.aungpyaesone.patient

import android.app.Application
import com.aungpyaesone.patient.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.PatientModelImpls

class CareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    CoreModelImpls.initDatabase(applicationContext)
    PatientModelImpls.initDatabase(applicationContext)

    SessionManager.init(applicationContext)
    }
}