package com.aungpyaesone.shared.data.vos

import com.google.gson.annotations.SerializedName

data class DataVO(
    @SerializedName("name")
    var title: String? = "",
    var body: String? = "",
    var id : String? = ""
)