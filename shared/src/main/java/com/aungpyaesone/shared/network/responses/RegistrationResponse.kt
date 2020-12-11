package com.aungpyaesone.shared.network.responses

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
        @SerializedName("notification_key")
        var notification_key : String? = ""
)
{
}