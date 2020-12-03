package com.aungpyaesone.patient.activities
import android.os.Bundle
import com.aungpyaesone.patient.R
import com.padc.shared.activites.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }
}