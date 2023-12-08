package com.example.souq.fragments.loginregester

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.souq.R
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class LoginRegisterActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login_register)
    }
}