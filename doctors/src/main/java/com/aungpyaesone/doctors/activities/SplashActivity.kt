package com.aungpyaesone.doctors.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.aungpyaesone.doctors.R
import com.aungpyaesone.doctors.utils.SessionManager
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