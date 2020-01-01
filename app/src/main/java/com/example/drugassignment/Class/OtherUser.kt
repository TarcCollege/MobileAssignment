package com.example.drugassignment.Class


class OtherUser(name : String, emails : String, citys : String, progressions : String) {

    var displayName : String
    var email : String
    var city : String
    var progression : String

    init {
        displayName = name
        email = emails
        city = citys
        progression = progressions
    }

}