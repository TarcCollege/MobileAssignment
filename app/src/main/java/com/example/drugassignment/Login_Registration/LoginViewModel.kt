package com.example.drugassignment.Login_Registration

import android.content.Context
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.random.Random


class LoginViewModel : ViewModel() {


    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = Transformations.map(FirebaseUserLiveData()) {
        if (it != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }


}
