package com.aungpyaesone.shared.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungpyaesone.shared.data.vos.SpecialQuestionVO
import io.reactivex.Completable

@Dao
interface SpecialQuestionDao {
    @Query("SELECT * FROM special_question")
    fun getAllSpecialQuestionList() : LiveData<List<SpecialQuestionVO>>

    @Query("SELECT * FROM special_question WHERE sq_id = :specialQuestionId")
    fun getSpecialQuestionById(specialQuestionId :String) : LiveData<SpecialQuestionVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpecialQuestion(specialQuestionVO: SpecialQuestionVO): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSpecialQuestionList(specialQuestionList: List<SpecialQuestionVO>) : Completable
}