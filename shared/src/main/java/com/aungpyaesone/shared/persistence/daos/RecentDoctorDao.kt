package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.DoctorVO
import com.aungpyaesone.shared.data.vos.RecentDoctorVO
import io.reactivex.Completable

@Dao
interface RecentDoctorDao {
    @Query("SELECT * FROM recently_doctor")
    fun getRecentDoctor() : LiveData<List<RecentDoctorVO>>

    @Query("SELECT * FROM recently_doctor WHERE id = :doctorId")
    fun getRecentDoctorById(doctorId :String) : LiveData<RecentDoctorVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecentDoctor(recentDoctor: RecentDoctorVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecentDoctorList(recentDoctorList: List<RecentDoctorVO>):Completable

    @Query("DELETE FROM recently_doctor")
    fun deleteAllRecentlyDoctor()
}