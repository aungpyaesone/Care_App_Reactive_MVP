package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.PatientVO
import io.reactivex.Completable

@Dao
interface PatientDao {
    @Query("SELECT * FROM patient")
    fun getPatient() : LiveData<List<PatientVO>>

    @Query("SELECT * FROM patient WHERE id = :patientId")
    fun getPatientById(patientId :String) : LiveData<PatientVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPatient(detail: PatientVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPatientList(detailList: List<PatientVO>): Completable

    @Query("select * from patient WHERE email = :email")
    fun getAllPatientDataByEmail(email: String): LiveData<PatientVO>

    @Query("DELETE FROM patient")
    fun deleteAllPatientData()
}