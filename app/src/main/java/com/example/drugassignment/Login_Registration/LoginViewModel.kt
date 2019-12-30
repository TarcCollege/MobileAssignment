package com.example.drugassignment.Login_Registration

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.drugassignment.Class.UserLog
import com.firebase.ui.auth.data.model.User
import kotlin.random.Random


class LoginViewModel : ViewModel() {

    lateinit var user : UserLog

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState: LiveData<AuthenticationState>? = Transformations.map(FirebaseUserLiveData()) {
        if (it != null && it.isEmailVerified) {
            user = UserLog(it)
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


}
