package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.ConsultationRequestVO
import io.reactivex.Completable

@Dao
interface ConsultationChatDao {
    @Query("SELECT * FROM consultation_chat")
    fun getConsultationChat() : LiveData<List<ConsultationChatVO>>

    @Query("SELECT * FROM consultation_chat WHERE cc_id = :consultChatId")
    fun getConsultationChatById(consultChatId :String) : LiveData<ConsultationChatVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationChat(consultationChatVO: ConsultationChatVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertConsultationChatList(ConsultationChatList: List<ConsultationChatVO>) : Completable
}
