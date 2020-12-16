package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.ConsultationChatVO
import com.aungpyaesone.shared.data.vos.GeneralQuestionVO
import io.reactivex.Completable

@Dao
interface GeneralQuestionTemplateDao {
    @Query("SELECT * FROM general_question")
    fun getAllGeneralQuestion() : LiveData<List<GeneralQuestionVO>>

    @Query("SELECT * FROM general_question WHERE id = :generalQuestionId")
    fun getGeneralQuestionById(generalQuestionId :String) : LiveData<GeneralQuestionVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGeneralQuestion(consultationChatVO: GeneralQuestionVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGeneralQuestionList(ConsultationChatList: List<GeneralQuestionVO>) : Completable

    @Query("delete from general_question")
    fun deleteAllGeneralQuestion()
}