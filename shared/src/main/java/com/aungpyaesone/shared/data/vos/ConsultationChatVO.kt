package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.CaseSummaryTypeConverter
import com.aungpyaesone.shared.persistence.ChatMessageTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties


@Entity(tableName = "consulation_chat")
@IgnoreExtraProperties
@TypeConverters(ChatMessageTypeConverter::class,CaseSummaryTypeConverter::class)
data class ConsultationChatVO(
    @PrimaryKey
    var cc_id: String= "",
    var patient_id: String = "",
    var doctor_id: String = "",
    var chatMessageVO: ArrayList<ChatMessageVO> = arrayListOf(),
    var caseSummaryVO:  ArrayList<CaseSummaryVO> = arrayListOf(),
)