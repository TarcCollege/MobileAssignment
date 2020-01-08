package com.example.drugassignment.Profile_Module.sub_module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drugassignment.Class.OtherUser
import com.example.drugassignment.Class.SubUser
import com.example.drugassignment.R
import kotlinx.android.synthetic.main.member_view_item.view.*


class MemberAdapter: RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    var data =  listOf<SubUser>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = layoutInflater
            .inflate(R.layout.member_view_item, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : SubUser = data[position]

        holder.email.text = item.email
        holder.name.text = item.displayName
        holder.address.text = item.address
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val email: TextView = itemView.findViewById(R.id.txtEmail)
        val address : TextView = itemView.findViewById(R.id.txtAddress)
        val name : TextView = itemView.findViewById(R.id.txtName)

    }


}