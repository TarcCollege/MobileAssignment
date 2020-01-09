package com.example.drugassignment.Class

import java.util.*

class Notification {
    var date : Date = Date()
    var content : String = "123"
    var view : Boolean = false

    constructor()
    constructor(date: Date, content: String, view : Boolean) {
        this.date = date
        this.content = content
        this.view = view
    }

}