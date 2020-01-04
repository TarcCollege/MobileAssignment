package com.example.drugassignment.Class

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class DrugDetail{
    var DrugName : String? = ""
    var DrugInfo : String? = ""
    var DrugSideEffect : String? = ""
    var DrugType : String? = ""

    constructor()
    constructor(name: String?, description: String?, sideEffect: String?, type: String?) {
        this.DrugName = name
        this.DrugInfo = description
        this.DrugSideEffect = sideEffect
        this.DrugType = type
    }


}