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
import com.example.drugassignment.Class.CurrentUser
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MentorMenteeAdapter constructor(context: Activity) :
    RecyclerView.Adapter<MentorMenteeAdapter.ViewHolder>() {

    var context = context

    var data = listOf<CurrentUser>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(R.layout.mentor_mentee_view_list, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: CurrentUser = data[position]

        holder.email.text = item.email
        holder.name.text = item.displayName
        holder.address.text = item.address

        holder.buttonbuttonViewMore.setOnClickListener {
            val intent = Intent(it.context, ProfileDetail::class.java)
            intent.putExtra("DisplayName", item.displayName)
            intent.putExtra("Email", item.email)
            it.context.startActivity(intent)
        }
        holder.buttonApply.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString(context.getString(R.string.passEmail), "123")
            val name =
                sharedPreferences.getString(context.getString(R.string.passDisplayName), "123")
            val date = sharedPreferences.getString(context.getString(R.string.passDate), "123")
            val address =
                sharedPreferences.getString(context.getString(R.string.passAddress), "123")
            val availability =
                sharedPreferences.getBoolean(context.getString(R.string.passAvailable), false)
            val role = sharedPreferences.getString(context.getString(R.string.passRole), "123")
            val targetEmail = item.email!!
            Log.i("share111", email)

            val mentorUser = SubUser(item.displayName, item.email, item.registerDate, item.address)
            val currentUser = SubUser()

            if (!availability && role == "Mentee") {
                Toast.makeText(
                    context, "You Can Only Apply For 1 Mentor",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                db.collection("User").document(email!!)
                    .collection("SubUser")
                    .document(item.email!!)
                    .set(mentorUser)           // add selected user
                    .addOnSuccessListener {
                        if (role == "Mentee") {
                            Toast.makeText(
                                context, "Successfully Added, Directing Back to Profile Page",
                                Toast.LENGTH_SHORT
                            ).show()

                            // add notification
                            val createTime = Date()
                            var content = "You have added $targetEmail as Mentee"
                            val notification = Notification(createTime, content, false)

                            db.collection("User")
                                .document(email!!)
                                .collection("Notification")
                                .document(createTime.toString())
                                .set(notification)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        context, "Successfully Add Noti",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            // update the mentor SubUser
                            db.collection("User")
                                .document(item.email!!)
                                .collection("SubUser")
                                .document(email)
                                .set(SubUser(name, email, date, address))

                            sharedPreferences.edit()
                                // update the availanility of current user on sharedPreference
                                .putBoolean(
                                    context.getString(com.example.drugassignment.R.string.passAvailable),
                                    false
                                )
                                .apply()

                            // update currentuser SubUser
                            db.collection("User").document(email!!)
                                .update("availability", false)
                                .addOnCompleteListener {
                                    // update the availability of the mentee
                                    if (it.isSuccessful) {
                                        context.finish()
                                    }
                                }

                        } else {

                            // add notification
                            val createTime = Date()
                            var content = "You have added $targetEmail as Mentor"
                            val notification = Notification(createTime, content, false)

                            db.collection("User")
                                .document(email!!)
                                .collection("Notification")
                                .document(createTime.toString())
                                .set(notification)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        context, "Successfully Add Noti",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            // update current user sharedPreferences
                            sharedPreferences.edit()
                                .putBoolean(context.getString(R.string.passAvailable), false)
                                .apply()
                            Toast.makeText(
                                context, "Successfully Added, Directing Back to Profile Page",
                                Toast.LENGTH_SHORT
                            ).show()

                            // update current user database availability

                            db.collection("User").document(item.email!!)
                                .update("availability", false)
                                .addOnCompleteListener {
                                    // update the availability of the mentee
                                    if (it.isSuccessful) {
                                        context.finish()
                                    }
                                }
                        }


                    }
            }


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView = itemView.findViewById(R.id.txtEmail)
        val address: TextView = itemView.findViewById(R.id.txtAddress)
        val name: TextView = itemView.findViewById(R.id.txtName)
        val buttonbuttonViewMore: Button = itemView.findViewById(R.id.buttonViewMore)
        val buttonApply: Button = itemView.findViewById(R.id.buttonApply)

    }

    private fun addUser() {


    }


}