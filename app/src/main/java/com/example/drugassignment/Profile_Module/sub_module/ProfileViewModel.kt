package com.example.drugassignment.Profile_Module.sub_module

import android.util.Log
import androidx.lifecycle.ViewModel

class ProfileViewModel2 : ViewModel() {
    var displayName : String
    var email : String
    var available :Boolean
    var address :String
    var role :String


    fun setData(displayName : String, email : String, available : Boolean, address : String, role : String) {
        this.displayName = displayName
        this.email = email
        this.available = available
        this.address = address
        this.role = role


    }

    init {
        displayName = "1"
        email = "1"
        available = false
        address = "1"
        role = "1"

        Log.i("role", role)
    }
}