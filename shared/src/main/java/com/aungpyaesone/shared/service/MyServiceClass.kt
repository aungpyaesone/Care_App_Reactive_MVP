package com.aungpyaesone.shared.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyServiceClass : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        // submit token to server
    }
}