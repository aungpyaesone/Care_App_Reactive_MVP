package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.PatientVO
import com.aungpyaesone.shared.data.vos.PrescriptionVO
import io.reactivex.Completable

@Dao
interface PrescriptionDao {
    @Query("SELECT * FROM prescription")
    fun getPrescription() : LiveData<List<PrescriptionVO>>

    @Query("SELECT * FROM prescription WHERE id = :prescriptionId")
    fun getPrescriptionById(prescriptionId :String) : LiveData<PrescriptionVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPrescriptionVO(detail: PrescriptionVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPrescriptionVOList(detailList: List<PrescriptionVO>): Completable

    @Query("DELETE FROM prescription")
    fun deletePrescription()
}