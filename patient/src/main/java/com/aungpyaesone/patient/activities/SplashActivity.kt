package com.aungpyaesone.patient.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.aungpyaesone.patient.R
import com.aungpyaesone.patient.utils.SessionManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.padc.shared.activites.BaseActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({
            if(!SessionManager.login_status) {
                startActivity(LoginActivity.newInstance(this))
            }else{
                startActivity(MainActivity.newInstance(this))
            }
            finish()
        },1800)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}