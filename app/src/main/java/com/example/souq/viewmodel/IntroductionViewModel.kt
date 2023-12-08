package com.example.souq.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.souq.R
import com.example.souq.util.Constants.INTODUCTION_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _navigate = MutableStateFlow(0)
    var navigate: StateFlow<Int> = _navigate

    companion object {
        const val SHOPPING_ACTIVITY = 23
            val ACCOUNT_OPTION_FRAGMENT =
            R.id.action_introductionFragment_to_acountOptionsFragment
    }

    init {
        val isButtonClicked = sharedPreferences.getBoolean(INTODUCTION_KEY, false)
        val user = firebaseAuth.currentUser
        if (user != null) {
            viewModelScope.launch {
                _navigate.emit(SHOPPING_ACTIVITY)
            }
        } else if (isButtonClicked) {
            viewModelScope.launch {
                _navigate.emit(ACCOUNT_OPTION_FRAGMENT)
            }
        } else {
            Unit
        }
    }

    fun startButtonClick() {
        sharedPreferences.edit().putBoolean(INTODUCTION_KEY,true).apply()
    }
}