package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import com.aungpyaesone.shared.data.vos.SpecialitiesVO
import io.reactivex.Completable

@Dao
interface ConsultationRequestDao {
    @Query("SELECT * FROM consultation_request")
    fun getConsultationRequest() : LiveData<List<ConsultationRequestVO>>

    @Query("SELECT * FROM consultation_request WHERE cr_id = :consulReqId")
    fun getConsultationRequestById(consulReqId :String) : LiveData<ConsultationRequestVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationRequest(consultationReqVO: ConsultationRequestVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationRequestList(consultationList: List<ConsultationRequestVO>) : Completable
}
