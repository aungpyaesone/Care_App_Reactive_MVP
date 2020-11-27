package com.aungpyaesone.shared.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatMessageVO(
    var sendAt: String= "",
    var sendBy: String = "",
    var messageText: String= "",
    var messageImage: String = ""
)