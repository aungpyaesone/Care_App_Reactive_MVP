package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import io.reactivex.Completable

@Dao
interface SpecialitiesDao {
    @Query("SELECT * FROM specialites")
    fun getSpecialities() : LiveData<List<SpecialitiesVO>>

    @Query("SELECT * FROM specialites WHERE sp_id = :specialtiesId")
    fun getSpecialitiesById(specialtiesId :String) : LiveData<SpecialitiesVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpecialities(detail: SpecialitiesVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpecialitiesList(detailList: List<SpecialitiesVO>) : Completable
}