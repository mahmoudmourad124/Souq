package com.example.souq.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.souq.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_login_register)
    }
}