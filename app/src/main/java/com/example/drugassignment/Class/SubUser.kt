package com.example.drugassignment.Class

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
class SubUser{
    var displayName : String? = ""
    var email : String? = ""
    var address : String? = ""
    var registerDate : String? = ""

    constructor()

    constructor(
        displayName: String?,
        email: String?,
        registerDate: String?,
        address : String?

    ) {
        this.displayName = displayName
        this.email = email
        this.registerDate = registerDate
        this.address = address
    }


}