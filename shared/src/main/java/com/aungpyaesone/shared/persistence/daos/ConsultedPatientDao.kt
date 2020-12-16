package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.ConsultedPatientVO
import io.reactivex.Completable

@Dao
interface ConsultedPatientDao {
    @Query("SELECT * FROM consulted_patient")
    fun getConsultedPatient() : LiveData<List<ConsultedPatientVO>>

    @Query("SELECT * FROM consulted_patient WHERE id = :patientId")
    fun getConsultedPatientById(patientId :String) : LiveData<ConsultedPatientVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultedPatient(detail: ConsultedPatientVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultedPatientList(detailList: List<ConsultedPatientVO>): Completable

    @Query("select * from consulted_patient WHERE email = :email")
    fun getAllPatientDataByEmail(email: String): LiveData<ConsultedPatientVO>

    @Query("DELETE FROM consulted_patient")
    fun deleteAllConsultedPatientData()
}