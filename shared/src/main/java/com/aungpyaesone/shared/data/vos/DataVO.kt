package com.aungpyaesone.shared.data.vos

import com.google.gson.annotations.SerializedName

data class DataVO(
    @SerializedName("name")
    var name: String? = "",
    var dob: String? = "",
    var id : String? = ""
)