package com.example.drugassignment.Information_Module

import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel() {
    var info : MutableList<String>? = null

    fun addInfo(info2 : String) {
        for (i in 1..20 ) {
           info?.add(info2)
        }
    }
}