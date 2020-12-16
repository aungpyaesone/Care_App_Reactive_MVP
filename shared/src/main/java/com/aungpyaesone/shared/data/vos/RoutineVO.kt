package com.aungpyaesone.shared.data.vos

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RoutineVO(
        var id:String? ="",
        var day: String? = "",
        var note: String? = "",
        var repeat: String? = "",
        var tab: String? = "",
        var amount : String? = "",
        var times: String? = null)