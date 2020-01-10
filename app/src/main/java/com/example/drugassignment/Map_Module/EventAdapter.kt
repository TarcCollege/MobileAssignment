package com.example.drugassignment.Map_Module

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.CreateDrugEvent
import com.example.drugassignment.CreateEvent
import com.example.drugassignment.Information_Module.InfoViewModel
import com.example.drugassignment.R
import java.text.SimpleDateFormat


class EventAdapter constructor(context: Fragment) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var context = context
    var data = listOf<CreateDrugEvent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.drugassignment.R.layout.event_list_layout, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CreateDrugEvent = data[position]
        var content: String = ""

        holder.creator.text = "Event Creator : " + item.createrEmail
        holder.name.text = "Event Name : " + item.eventName
        holder.endTime.text =
            "Event End Time : " + SimpleDateFormat("HH : mm a").format(item.endTime)
        holder.startTime.text =
            "Event Start Time : " + SimpleDateFormat("HH : mm a").format(item.startTime)
        holder.date.text = "Event Date : " + SimpleDateFormat("dd/MM/yyyy").format(item.eventDate)
        holder.location.text = "Event Location : " + item.eventLocation

        holder.viewMore.setOnClickListener {

            val viewModel = ViewModelProviders.of(context.activity!!).get(EventViewModel::class.java)
            viewModel.setData(
                CreateDrugEvent(item.eventName,item.eventDescription,item.eventLocation
                ,item.eventCity,item.createrEmail,item.eventDate,item.startTime
                ,item.endTime)
            )

            Log.i("eventName", viewModel.detail.eventName)

            it.findNavController().navigate(R.id.action_eventMain_to_eventDetail)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val creator: TextView =
            itemView.findViewById(com.example.drugassignment.R.id.displayCreator)
        val name: TextView = itemView.findViewById(com.example.drugassignment.R.id.displayEventName)
        val location: TextView =
            itemView.findViewById(com.example.drugassignment.R.id.displayEventLocation)
        val date: TextView = itemView.findViewById(com.example.drugassignment.R.id.displayDate)
        val startTime: TextView =
            itemView.findViewById(com.example.drugassignment.R.id.displayStartTime)
        val endTime: TextView =
            itemView.findViewById(com.example.drugassignment.R.id.displayEndTimeTime)
        val viewMore: Button = itemView.findViewById(com.example.drugassignment.R.id.buttonVIewMore2)
    }
}