package com.example.drugassignment.Class

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class DrugDetail{
    var drugName : String? = ""
    var drugInfo : String? = ""
    var drugSideEffect : String? = ""
    var drugType : String? = ""

    constructor()
    constructor(name: String?, description: String?, sideEffect: String?, type: String?) {
        this.drugName = name
        this.drugInfo = description
        this.drugSideEffect = sideEffect
        this.drugType = type
    }


}