package com.example.drugassignment.Profile_Module.sub_module

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Class.SubUser

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat

class NotificationAdapter  constructor(context: FragmentActivity?) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    var context = context
    var data = listOf<Notification>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.drugassignment.R.layout.notification_layout, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("123", data.size.toString())
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Notification = data[position]



        holder.date.text = SimpleDateFormat("dd/MM/yyyy").format(item.date)
        holder.content.text = item.content

        if (item.view) {
            holder.wrapper.setCardBackgroundColor(Color.WHITE)
        }

        holder.wrapper.setOnClickListener {
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
            val email = sharedPreferences?.getString(context?.getString(com.example.drugassignment.R.string.passEmail), "123")


            db.collection("User")
                .document(email!!)
                .collection("Notification")
                .document(item.date.toString())
                .update("view", true)
                .addOnCompleteListener {


                    holder.wrapper.setCardBackgroundColor(Color.WHITE)
                }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(com.example.drugassignment.R.id.textContent)
        val date: TextView = itemView.findViewById(com.example.drugassignment.R.id.textDate)
        val wrapper: CardView = itemView.findViewById(com.example.drugassignment.R.id.holderNoti)

    }
}


