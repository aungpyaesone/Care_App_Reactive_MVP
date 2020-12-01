package com.aungpyaesone.shared.data.vos

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RoutineVO(
        var day: String? = "",
        var note: String? = "",
        var repeat: String? = "",
        var tab: String? = "",
        var times: Timestamp? = null)