package com.example.drugassignment.Login_Registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.drugassignment.Class.UserLog



class LoginViewModel : ViewModel() {

    lateinit var user : UserLog
    var login = false

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState: LiveData<AuthenticationState>? = Transformations.map(FirebaseUserLiveData()) {
        if (it != null && it.isEmailVerified) {
            user = UserLog(it)
            login = true
            AuthenticationState.AUTHENTICATED
        } else {
            login = false
            AuthenticationState.UNAUTHENTICATED
        }
    }



}
