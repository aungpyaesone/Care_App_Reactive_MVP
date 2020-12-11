package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.DoctorVO
import io.reactivex.Completable

@Dao
interface DoctorDao {
    @Query("SELECT * FROM doctor")
    fun getDoctor() : LiveData<List<DoctorVO>>

    @Query("SELECT * FROM doctor WHERE id = :doctorId")
    fun getDoctorById(doctorId :String) : LiveData<DoctorVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDoctor(detail: DoctorVO):Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDoctorList(detailList: List<DoctorVO>):Completable

    @Query("select * from doctor WHERE email = :email")
    fun getAllDoctorDataByEmail(email: String): LiveData<DoctorVO>
}