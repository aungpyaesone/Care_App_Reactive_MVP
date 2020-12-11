package com.aungpyaesone.shared.network

import com.aungpyaesone.shared.data.vos.NotificationVO
import com.aungpyaesone.shared.data.vos.RegistrationAddRequest
import com.aungpyaesone.shared.network.responses.NotiResponse
import com.aungpyaesone.shared.network.responses.RegistrationResponse
import com.aungpyaesone.shared.util.API_KEY
import com.aungpyaesone.shared.util.PROJECT_ID
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:$API_KEY"
    )
    @POST("fcm/send")
    fun sendFcm(@Body notificationVO: NotificationVO) : Observable<NotiResponse>

    @Headers(
        "Authorization:$API_KEY",
        "Content-Type:application/json",
        "project_id:$PROJECT_ID"
    )
    @POST("fcm/notification")
    fun addRegistrationToken(@Body addRequest: RegistrationAddRequest) : Observable<RegistrationResponse>
}