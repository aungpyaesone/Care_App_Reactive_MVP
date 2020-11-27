package com.aungpyaesone.shared.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.persistence.daos.DoctorDao
import com.aungpyaesone.shared.persistence.daos.MedicineDao
import com.aungpyaesone.shared.persistence.daos.PatientDao

@Database(entities = [DoctorVO::class,
    PatientVO::class,
    MedicineVO::class,
    ConsultationChatVO::class,
    ConsultationRequestVO::class,
    GeneralQuestionTemplateVO::class,
    GeneralQuestionVO::class,
    SpecialitiesVO::class,
    SpecialQuestionVO::class
],version = 1,exportSchema = false)
abstract class CareDatabase : RoomDatabase(){
    companion object {
        val DB_NAME = "CARE_DB"
        var dbInstance: CareDatabase? = null

        fun getDBInstance(context: Context): CareDatabase {
        when (dbInstance) {
                null -> {
                    dbInstance = Room.databaseBuilder(context, CareDatabase::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            val i = dbInstance
            return i!!
        }
    }

    abstract fun doctorDao():DoctorDao
    abstract fun patientDao():PatientDao
    abstract fun medicineDao():MedicineDao
}