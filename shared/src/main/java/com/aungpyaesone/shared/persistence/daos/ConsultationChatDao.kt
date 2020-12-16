package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import io.reactivex.Completable

@Dao
interface ConsultationChatDao {
    @Query("SELECT * FROM consultation_chat")
    fun getConsultationChat() : LiveData<List<ConsultationChatVO>>

    @Query("SELECT * FROM consultation_chat WHERE id = :consultChatId")
    fun getConsultationChatById(consultChatId :String) : LiveData<ConsultationChatVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationChat(consultationChatVO: ConsultationChatVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationChatList(ConsultationChatList: List<ConsultationChatVO>) : Completable

    @Query("delete from consultation_chat where id = :consultReqId")
    fun deleteAllConsultationChatById(consultReqId: String): Completable

    @Query("delete from consultation_chat")
    fun deleteAllConsultationChat()

}
