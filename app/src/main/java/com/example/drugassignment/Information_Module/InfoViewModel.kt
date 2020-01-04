package com.example.drugassignment.Information_Module

import androidx.lifecycle.ViewModel
import com.example.drugassignment.Class.DrugDetail

class InfoViewModel : ViewModel() {
    var drug : DrugDetail? = null


    fun setingDrug(drug: DrugDetail) {
        this.drug = drug
    }

}