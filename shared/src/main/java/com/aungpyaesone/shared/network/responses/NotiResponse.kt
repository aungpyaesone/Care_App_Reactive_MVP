package com.aungpyaesone.shared.network.responses

import com.google.gson.annotations.SerializedName

data class NotiResponse(
        @SerializedName("success")
        var success: String? = "",
        @SerializedName("failure")
        var failure: String? =""
) {
}