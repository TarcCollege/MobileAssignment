package com.example.drugassignment.Profile_Module

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var textView = view.findViewById<TextView>(android.R.id.text1)

    fun bindData(text: String){
        textView.text = text
    }

}