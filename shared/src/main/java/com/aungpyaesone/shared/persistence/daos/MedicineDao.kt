package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.MedicineVO


@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicine")
    fun getMedicineList() : LiveData<List<MedicineVO>>

    @Query("SELECT * FROM medicine WHERE id = :medicineId")
    fun getMedicineById(medicineId :String) : LiveData<MedicineVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMedicine(detail: MedicineVO)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMedicineList(detailList: List<MedicineVO>)
}