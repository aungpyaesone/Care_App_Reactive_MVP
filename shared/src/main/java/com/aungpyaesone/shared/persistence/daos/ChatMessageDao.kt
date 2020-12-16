package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aungpyaesone.shared.data.vos.ChatMessageVO
import com.google.firebase.firestore.IgnoreExtraProperties
import io.reactivex.Completable

@IgnoreExtraProperties
@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_message order by sendAt")
    fun getAllChatMessage() : LiveData<List<ChatMessageVO>>

    @Query("SELECT * FROM chat_message WHERE id = :chatId")
    fun getChatMessageById(chatId :String) : LiveData<ChatMessageVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChatMessage(chatMessage: ChatMessageVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChatMessageList(ConsultationChatList: List<ChatMessageVO>) : Completable

    @Query("delete from chat_message")
    fun deleteAllChatMessage()
}