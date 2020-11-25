package com.aungpyaesone.shared.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class RoutineVO(
        var id: String? ="",
        var name: String? = "",
        var times: Long? = 0
)