package com.aungpyaesone.doctors

import android.app.Application
import com.aungpyaesone.doctors.utils.SessionManager
import com.aungpyaesone.shared.data.models.impls.CoreModelImpls
import com.aungpyaesone.shared.data.models.impls.DoctorModelImpls

class CareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    CoreModelImpls.initDatabase(applicationContext)
    DoctorModelImpls.initDatabase(applicationContext)
    SessionManager.init(applicationContext)
    }
}