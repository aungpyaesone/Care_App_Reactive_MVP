package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import io.reactivex.Completable

@Dao
interface ConsultationRequestDao {
    @Query("SELECT * FROM consultation_request where status =='new'")
    fun getConsultationRequest() : LiveData<List<ConsultationRequestVO>>

    @Query("SELECT * FROM consultation_request WHERE id = :consulReqId")
    fun getConsultationRequestById(consulReqId :String) : LiveData<ConsultationRequestVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationRequest(consultationReqVO: ConsultationRequestVO): Completable

    @Query("delete from consultation_request")
    fun deleteAllConsultationRequest(): Completable

    @Query("delete from consultation_request where id = :consultReqId")
    fun deleteAllConsultationRequestById(consultReqId: String): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationRequestList(consultationList: List<ConsultationRequestVO>) : Completable

    @Query("select * from consultation_request where status=='accept'")
    fun getDoctorAcceptConsultationRequest(): LiveData<List<ConsultationRequestVO>>
}
