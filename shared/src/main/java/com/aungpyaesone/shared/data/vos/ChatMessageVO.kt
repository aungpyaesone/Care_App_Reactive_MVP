package com.aungpyaesone.shared.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.aungpyaesone.shared.persistence.converters.SenderTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties

@Entity(tableName = "chat_message")
@IgnoreExtraProperties
@TypeConverters(SenderTypeConverter::class)
data class ChatMessageVO(
    @PrimaryKey
    var id : String ="",
    var sendAt: String?= "",
    var sentBy: SenderVO? = null,
    var messageText: String= "",
    var messageImage: String = ""
)