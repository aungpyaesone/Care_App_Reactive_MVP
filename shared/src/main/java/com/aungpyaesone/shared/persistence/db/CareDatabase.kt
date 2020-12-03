package com.aungpyaesone.shared.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aungpyaesone.shared.data.vos.*
import com.aungpyaesone.shared.persistence.daos.*

@Database(entities = [DoctorVO::class,
    PatientVO::class,
    MedicineVO::class,
    ConsultationChatVO::class,
    ConsultationRequestVO::class,
    GeneralQuestionVO::class,
    SpecialitiesVO::class,
    SpecialQuestionVO::class,
    PrescriptionVO::class,
    CheckOutVO::class,
    ChatMessageVO::class
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
    abstract fun specialitiesDao() : SpecialitiesDao
    abstract fun consultationReqDao() : ConsultationRequestDao
    abstract fun consultationChatDao() : ConsultationChatDao
    abstract fun generalQuestionTemplateDao() : GeneralQuestionTemplateDao
    abstract fun specialQuestionDao() : SpecialQuestionDao
}