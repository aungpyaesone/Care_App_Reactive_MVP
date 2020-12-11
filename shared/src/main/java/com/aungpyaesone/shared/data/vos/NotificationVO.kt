package com.aungpyaesone.shared.data.vos

import com.google.gson.annotations.SerializedName

data class NotificationVO(
    @SerializedName("to")
    var to:String? = "",
    @SerializedName("data")
    var data : DataVO? = null
)


data class RegistrationAddRequest(
        @SerializedName("operation")
        var operation:String? ="",
        @SerializedName("notification_key_name")
        var notification_key_name:String? ="",
        @SerializedName("notification_key")
        var notification_key : String? ="",
        @SerializedName("registration_ids")
        var registration_ids : ArrayList<String> = arrayListOf()
)