package com.example.drugassignment.Class

import com.google.firebase.auth.FirebaseUser

class UserLog(user : FirebaseUser?) {

    var displayName = user?.displayName
    var email = user?.email

    init {
         displayName = user?.displayName
         email = user?.email
    }

}