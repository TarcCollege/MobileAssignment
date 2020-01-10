package com.example.drugassignment.Profile_Module.sub_module

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class ReminderAdapter constructor(context: Activity) :
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    var context = context

    var data = listOf<CreateDrugEvent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(R.layout.reminder_layou, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.i("123456", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CreateDrugEvent = data[position]

        holder.reminder.text = "You have an Event : \n" +
                "Event Name : " + item.eventName + "\n" +
                "Location : " + item.eventLocation

        Log.i("123456", item.eventName)

        holder.date.text = SimpleDateFormat("dd/MM/yyyy  HH : mm a").format(item.startTime)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminder: TextView = itemView.findViewById(R.id.textReminder)
        val date: TextView = itemView.findViewById(R.id.textDate)

    }

}


