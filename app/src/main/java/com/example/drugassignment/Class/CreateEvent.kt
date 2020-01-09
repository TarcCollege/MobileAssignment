package com.example.drugassignment.Class

import java.util.*

class CreateDrugEvent {
    var eventName: String? = "123"
    var eventDescription: String? = "123"
    var eventLocation: String? = "123"
    var eventCity: String? = "123"
    var createrEmail : String? = "123"
    var eventDate: Date = Date()
    var startTime: Date = Date()
    var endTime: Date = Date()


    constructor()
    constructor(
        eventName: String?,
        eventDescription: String?,
        eventLocation: String?,
        eventCity: String?,
        createrEmail : String?,
        eventDate: Date,
        startTime: Date,
        endTime: Date
    ) {
        this.eventName = eventName
        this.eventDescription = eventDescription
        this.eventLocation = eventLocation
        this.eventCity = eventCity
        this.createrEmail = createrEmail
        this.eventDate = eventDate
        this.startTime = startTime
        this.endTime = endTime
    }
}

//    fun addData(
//        eventName: String?,
//        eventDescription: String?,
//        eventCity: String?,
//        eventDate: Date,
//        startTime: Date,
//        endTime: Date
//    ) {
//        this.eventName = eventName
//        this.eventDescription = eventDescription
//        this.eventCity = eventCity
//        this.eventDate = eventDate
//        this.startTime = startTime
//        this.endTime = endTime
//    }
