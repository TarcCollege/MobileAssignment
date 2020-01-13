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
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.Notification
import com.example.drugassignment.Class.SubUser

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*


class MemberAdapter constructor(context: FragmentActivity?) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    var context = context

    var data = listOf<SubUser>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.drugassignment.R.layout.mentor_mentee_view_list, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: SubUser = data[position]
        var content : String = ""

        holder.email.text = item.email
        holder.name.text = item.displayName
        holder.address.text = item.address
        holder.buttonApply.text = "Remove"

        holder.buttonbuttonViewMore.setOnClickListener {
            val intent = Intent(it.context, ProfileDetail::class.java)
            intent.putExtra("DisplayName", item.displayName)
            intent.putExtra("Email", item.email)
            it.context.startActivity(intent)
        }


        // unsolveable for removing item cause it getting data from cache
        // the cache data will still be around for some time
        holder.buttonApply.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            val role = sharedPreferences?.getString(context?.getString(com.example.drugassignment.R.string.passRole),"123")
            val email = sharedPreferences?.getString(context?.getString(com.example.drugassignment.R.string.passEmail), "123")
            val targetEmail = item.email!!

            // delete the SubUser from currentUser
            db.collection("User")
                .document(email!!)
                .collection("SubUser")
                .document(item.email!!)
                .delete()
                .addOnSuccessListener {
                    sharedPreferences.edit()
                            // update the availanility of current user on sharedPreference
                        .putBoolean(context?.getString(com.example.drugassignment.R.string.passAvailable), true)
                        .apply()
                    Toast.makeText(
                        context, "Successfully Deleted",
                        Toast.LENGTH_SHORT
                    ).show()

                    // update the availanility on firestore

                    if (role == "Mentor") {
                        // if is the deleted is mentee , change his availability to true

                        db.collection("User").document(item.email!!)
                            .update("availability", true)

                        content = "You have remove $targetEmail as Mentee"
                    } else {
                        content = "You have remove $targetEmail as Mentor"
                    }

                    db.collection("User").document(email!!)
                        .update("availability", true)


                    // delete currentUser from the mentorUser
                    db.collection("User")
                        .document(item.email!!)
                        .collection("SubUser")
                        .document(email)
                        .delete()

                    // add notification
                    val createTime = Date()
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
                }
            context?.buttonOtherUser?.isVisible = true
            holder.buttonApply.isEnabled = false
            holder.buttonbuttonViewMore.isEnabled = false




        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView = itemView.findViewById(com.example.drugassignment.R.id.txtEmail)
        val address: TextView = itemView.findViewById(com.example.drugassignment.R.id.txtAddress)
        val name: TextView = itemView.findViewById(com.example.drugassignment.R.id.txtName)
        val buttonbuttonViewMore: Button = itemView.findViewById(com.example.drugassignment.R.id.buttonViewMore)
        val buttonApply: Button = itemView.findViewById(com.example.drugassignment.R.id.buttonApply)
    }
}