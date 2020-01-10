package com.example.drugassignment.Map_Module

import androidx.lifecycle.ViewModel
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.CreateEvent

class EventViewModel : ViewModel() {
    var detail : CreateDrugEvent = CreateDrugEvent()

    fun setData(detail: CreateDrugEvent) {
        this.detail = detail
    }

}